/*
 * Copyright (c) 2023.
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
 */

package dev.mbo.osgi.service.impl;

import dev.mbo.osgi.service.api.Greeter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.Hashtable;

@SuppressWarnings("unused")
public class Activator implements BundleActivator {

    @SuppressWarnings("FieldCanBeLocal")
    private ServiceReference<Greeter> reference;
    private ServiceRegistration<Greeter> registration;

    @Override
    public void start(BundleContext context) {
        System.out.println("service: start");
        registration = context.registerService(
                Greeter.class,
                new GreeterImpl(),
                new Hashtable<String, String>());
        reference = registration.getReference();

    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("service: stop");
        registration.unregister();
    }
}