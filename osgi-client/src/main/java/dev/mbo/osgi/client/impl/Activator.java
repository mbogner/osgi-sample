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

package dev.mbo.osgi.client.impl;

import dev.mbo.osgi.service.api.Greeter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator, ServiceListener {

    private BundleContext context;
    private ServiceReference<?> serviceReference;

    @Override
    public void start(BundleContext context) {
        this.context = context;
        try {
            context.addServiceListener(this, "(objectClass=" + Greeter.class.getName() + ")");
            System.out.println("client: started");
        } catch (final InvalidSyntaxException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void stop(BundleContext context) {
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
        System.out.println("client: stopped");
    }

    @Override
    public void serviceChanged(ServiceEvent event) {
        int type = event.getType();
        switch (type) {
            case (ServiceEvent.REGISTERED) -> {
                System.out.println("client::notification: service started");
                serviceReference = event.getServiceReference();
                Greeter service = (Greeter) (context.getService(serviceReference));
                System.out.println(service.sayHiTo("mbo"));
            }
            case (ServiceEvent.UNREGISTERING) -> {
                System.out.println("client::notification: service stopped");
                context.ungetService(event.getServiceReference());
            }
            default -> {
                // do nothing
            }
        }
    }
}