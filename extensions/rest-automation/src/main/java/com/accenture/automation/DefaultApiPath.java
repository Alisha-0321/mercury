package com.accenture.automation;

import org.platformlambda.core.annotations.BeforeApplication;
import org.platformlambda.core.models.EntryPoint;
import org.platformlambda.core.util.AppConfigReader;
import org.platformlambda.core.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@BeforeApplication(sequence = 2)
public class DefaultApiPath implements EntryPoint {
    private static final Logger log = LoggerFactory.getLogger(DefaultApiPath.class);

    private static final String JAX_RS_PATH = "jax.rs.application.path";
    private static final String API_PATH = "api";
    private static final String DUMMY_PATH = "/_";

    @Override
    public void start(String[] args) {
        AppConfigReader config = AppConfigReader.getInstance();
        if (config.exists(JAX_RS_PATH)) {
            String path = config.getProperty(JAX_RS_PATH);
            List<String> parts = Utility.getInstance().split(path, "/");
            if (parts.size() == 1 && parts.get(0).equalsIgnoreCase(API_PATH)) {
                log.warn("Change JAX-RS path from /{} to {}", parts.get(0).toLowerCase(), DUMMY_PATH);
                System.setProperty(JAX_RS_PATH, DUMMY_PATH);
            }

        } else {
            // set default JAX-RS path to "/_" so it would interfere with REST automation path
            log.info("Set JAX-RS path to {}", DUMMY_PATH);
            System.setProperty(JAX_RS_PATH, DUMMY_PATH);
        }

    }
}
