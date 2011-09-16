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
package org.scribble.commandline.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator for the command line.
 *
 */
public class HostActivator implements BundleActivator {
    
    private BundleContext _context = null;

    /**
     * {@inheritDoc}
     */
    public void start(BundleContext context) {
        _context = context;
    }

    /**
     * {@inheritDoc}
     */
    public void stop(BundleContext context) {
        _context = null;
    }

    /**
     * {@inheritDoc}
     */
    public BundleContext getContext() {
        return _context;
    }
}
