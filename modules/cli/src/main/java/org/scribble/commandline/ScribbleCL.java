/*
 * Copyright 2009 www.scribble.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.scribble.commandline;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.main.AutoProcessor;
import org.apache.felix.main.Main;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.ServiceTracker;
import org.scribble.command.Command;
import org.scribble.commandline.osgi.HostActivator;

/**
 * This class provide the command line functionality.
 *
 */
public class ScribbleCL {
    private HostActivator _activator = null;
    private Felix _felix = null;
    private ServiceTracker _tracker = null;

    /**
     * The main function.
     * 
     * @param args The arguments
     */
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.err.println("Command must be specified as first parameter");
            System.exit(1);
        }
        
        try {
            ScribbleCL scl=new ScribbleCL();
            
            String[] parameters=new String[args.length-1];
            
            for (int i=1; i < args.length; i++) {
                parameters[i-1] = args[i];
            }
            
            if (!scl.execute(args[0], parameters)) {
                System.err.println("Command not executed");
            }
            
            scl.shutdownApplication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * The default constructor.
     * 
     */
    public ScribbleCL() {
        
        // Create a configuration property map.
        //Map configMap = new HashMap();
        Main.loadSystemProperties();

        java.util.Properties configProps = Main.loadConfigProperties();
        if (configProps == null) {
            System.err.println("No config.properties found.");
            configProps = new java.util.Properties();
        }

        Main.copySystemProperties(configProps);

        // Export the host provided service interface package.
        configProps.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
                    "org.scribble.command");
        
        // Create host activator;
        _activator = new HostActivator();
        List<Object> list = new ArrayList<Object>();
        list.add(_activator);
        configProps.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);
        
        String storage=System.getProperty("java.io.tmpdir")+File.separatorChar
                +"scribble"+File.separatorChar+"felix-cache";
        configProps.put(Constants.FRAMEWORK_STORAGE, storage);

        configProps.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);
        
        try {
            // Now create an instance of the framework with
            // our configuration properties.
            _felix = new Felix(configProps);

            _felix.init();
            
            AutoProcessor.process(configProps, _felix.getBundleContext());
            
            // Now start Felix instance.
            _felix.start();
        } catch (Exception ex) {
            System.err.println("Could not create framework: " + ex);
            ex.printStackTrace();
        }

        _tracker = new ServiceTracker(_activator.getContext(),
                org.scribble.command.Command.class.getName(), null);
        
        _tracker.open();
    }

    /**
     * The method for executing the command.
     * 
     * @param name The command name
     * @param args The arguments
     * @return Whether the command executed
     */
    public boolean execute(String name, String[] args) {
        // See if any of the currently tracked command services
        // match the specified command name, if so then execute it.
        Object[] services = _tracker.getServices();
        
        for (int i = 0; (services != null) && (i < services.length); i++) {
            try {
                if (((Command) services[i]).getName().equals(name)) {
                    return ((Command) services[i]).execute(args);
                }
            } catch (Exception ex) {
                // Since the services returned by the tracker could become
                // invalid at any moment, we will catch all exceptions, log
                // a message, and then ignore faulty services.
                ex.printStackTrace();
            }
        }
        
        return false;
    }

    /**
     * This method shuts down the application.
     * 
     * @throws Exception Failed
     */
    public void shutdownApplication() throws Exception {
        // Shut down the felix framework when stopping the
        // host application.
        _felix.stop();
        _felix.waitForStop(5000);
    }
}
