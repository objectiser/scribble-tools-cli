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
import java.util.List;
import java.util.ArrayList;
import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.ServiceTracker;
import org.apache.felix.main.AutoProcessor;
import org.apache.felix.main.Main;

import org.scribble.command.*;
import org.scribble.commandline.osgi.HostActivator;

public class ScribbleCL {
    private HostActivator m_activator = null;
    private Felix m_felix = null;
    private ServiceTracker m_tracker = null;

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
    		
    		if (scl.execute(args[0], parameters) == false) {
    			System.err.println("Command not executed");
    		}
    		
    		scl.shutdownApplication();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
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
        m_activator = new HostActivator();
        List<Object> list = new ArrayList<Object>();
        list.add(m_activator);
        configProps.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);
        
        String storage=System.getProperty("java.io.tmpdir")+File.separatorChar+
        					"scribble"+File.separatorChar+"felix-cache";
        configProps.put(Constants.FRAMEWORK_STORAGE, storage);

        try {
            // Now create an instance of the framework with
            // our configuration properties.
            m_felix = new Felix(configProps);

            m_felix.init();
            
        	AutoProcessor.process(configProps, m_felix.getBundleContext());
        	
            // Now start Felix instance.
            m_felix.start();
        } catch (Exception ex) {
            System.err.println("Could not create framework: " + ex);
            ex.printStackTrace();
        }

        m_tracker = new ServiceTracker(m_activator.getContext(),
        		org.scribble.command.Command.class.getName(), null);
        
        m_tracker.open();
    }

    public boolean execute(String name, String args[]) {
        // See if any of the currently tracked command services
        // match the specified command name, if so then execute it.
        Object[] services = m_tracker.getServices();
        
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

    public void shutdownApplication() throws Exception {
        // Shut down the felix framework when stopping the
        // host application.
        m_felix.stop();
        m_felix.waitForStop(5000);
    }
}