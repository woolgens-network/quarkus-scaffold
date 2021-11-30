package net.woolgens.scaffold.auth;

import io.quarkus.runtime.StartupEvent;
import net.woolgens.library.auth.AuthBootstrap;

import javax.enterprise.event.Observes;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Provider
@PreMatching
public class AuthWrapper implements ContainerRequestFilter {

    AuthBootstrap bootstrap;

    public void onStart(@Observes StartupEvent event) {
        try {
            this.bootstrap = new AuthBootstrap("config/");
        }catch (Exception exception){
            System.out.println("There was an error while trying to initialize auth bootstrap");
        }
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if(bootstrap != null) {
            bootstrap.getProvider().getSecurity().handleQuarkusSecurity(containerRequestContext);
        }
    }
}
