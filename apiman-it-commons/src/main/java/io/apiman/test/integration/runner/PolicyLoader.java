/*
 * Copyright 2016 Red Hat Inc.
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

package io.apiman.test.integration.runner;

import io.apiman.test.integration.SuiteProperties;
import io.apiman.test.integration.runner.annotations.misc.Policies;
import io.apiman.manager.api.beans.policies.NewPolicyBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.text.StrSubstitutor;

/**
 * @author jcechace
 */
public class PolicyLoader {

    private String file;
    private Map<String, String> params;

    public PolicyLoader(String file, Map<String, String> params) {
        this.file = file;
        this.params = params;
    }

    /**
     *
     * @param policies annotation containing configuration
     * @return
     */
    public static PolicyLoader from(Policies policies) {
        if (policies.value().isEmpty()) {
            return null;
        }

        String[] raw = policies.params();

        if (raw.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid parameter array provided in @Policies");
        }

        Map<String, String> params = new HashMap<>();

        for (int i = 0; i < raw.length; i += 2) {
            params.put(raw[i], raw[i + 1]);

        }

        return new PolicyLoader(policies.value(), params);
    }

    public List<NewPolicyBean> load() {
        List<NewPolicyBean> policies = new LinkedList<>();
        try {
            load(file, policies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return policies;
    }

    private void storeBean(String definitionId, String config, List<NewPolicyBean> policies) {
        config = StrSubstitutor.replace(config, System.getProperties());
        config = StrSubstitutor.replace(config, SuiteProperties.getProperties());
        config = StrSubstitutor.replace(config, params);

        NewPolicyBean bean = new NewPolicyBean();
        bean.setDefinitionId(definitionId);
        bean.setConfiguration(config);

        policies.add(bean);
    }

    private List<NewPolicyBean> load(String file, List<NewPolicyBean> policies) throws IOException {
        try (InputStream is = PolicyLoader.class.getResourceAsStream("/policies/" + file + ".pcfg")) {
            LineIterator lines = IOUtils.lineIterator(is, "utf8");

            StringBuilder config = new StringBuilder();
            String definitionId = null;
            boolean cfgRead = false;

            while (lines.hasNext()) {
                String line = lines.next();

                if (line.startsWith("---")) { // Policy config starts on the next line
                    cfgRead = true;
                    continue;
                }

                if (line.startsWith("@")) { // Include another file
                    String include = line.substring(line.indexOf("@") + 1);
                    load(include.trim(), policies);
                    continue;
                }

                if (cfgRead) { // reading policy config
                    config.append(line);
                } else {  // reading definition definitionId
                    definitionId = line.trim();
                }

                if (!lines.hasNext() || (line.isEmpty() && cfgRead)) {  // Policy config ended on previous line
                    storeBean(definitionId, config.toString(), policies);
                    config = new StringBuilder();
                    cfgRead = false;
                }
            }
        }
        return policies;
    }
}
