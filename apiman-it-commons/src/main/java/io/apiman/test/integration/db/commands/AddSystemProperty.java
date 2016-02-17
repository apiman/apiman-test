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

package io.apiman.test.integration.db.commands;

import java.io.IOException;

import org.jboss.qa.creaper.commands.foundation.offline.xml.GroovyXmlTransform;
import org.jboss.qa.creaper.commands.foundation.offline.xml.Subtree;
import org.jboss.qa.creaper.core.CommandFailedException;
import org.jboss.qa.creaper.core.offline.OfflineCommand;
import org.jboss.qa.creaper.core.offline.OfflineCommandContext;

/**
 * Creaper command to add system property
 *
 * @author jcechace
 */
public class AddSystemProperty implements OfflineCommand {

    public final String propertyName;
    public final String value;
    public final boolean replaceExisting;

    public AddSystemProperty(Builder builder) {
        this.propertyName = builder.propertyName;
        this.value = builder.value;
        this.replaceExisting = builder.replaceExisting;
    }

    @Override
    public void apply(OfflineCommandContext ctx) throws CommandFailedException, IOException {
        GroovyXmlTransform transform = GroovyXmlTransform
                .of(AddSystemProperty.class)
                .subtree("systemProperties", Subtree.systemProperties())
                .parameter("propertyName", propertyName)
                .parameter("value", value)
                .parameter("replaceExisting", replaceExisting)
                .build();

        ctx.client.apply(transform);
    }

    @Override
    public String toString() {
        return "AddSystemProperty " + propertyName;
    }

    public static final class Builder {
        private final String propertyName;

        private String value;
        private boolean replaceExisting = false;

        public Builder(String propertyName) {
            if (propertyName == null) {
                throw new IllegalArgumentException("Name of the property must be specified as non null value");
            }
            this.propertyName = propertyName;
        }

        /**
         * Specify whether to replace the existing property based on its name.
         * By default existing property is not replaced and exception is thrown.
         */
        public Builder replaceExisting() {
            this.replaceExisting = true;
            return this;
        }

        /**
         * Sets the property value.
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public AddSystemProperty build() {
            return new AddSystemProperty(this);
        }
    }
}
