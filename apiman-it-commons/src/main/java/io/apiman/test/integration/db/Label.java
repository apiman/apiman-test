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

package io.apiman.test.integration.db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jcechace
 */
public class Label {

    private String label;

    public Label(String label) {
        this.label = label;
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * parse database name from label
     *
     * @return db name
     */
    public String getName() {
        return getLabelPart(1);
    }

    /**
     * parse major version of db from label
     *
     * @return db major version
     */
    public int getMajorVersion() {
        return Integer.valueOf(getLabelPart(2));
    }

    /**
     * parse minor version of db from label
     *
     * @return db minor version
     */
    public int getMinorVersion() {
        return Integer.valueOf(getLabelPart(3));
    }

    private String getLabelPart(int part) {
        Matcher matcher = Pattern.compile("^(\\D+)(\\d+)(\\d)+$").matcher(label);
        matcher.find();

        return matcher.group(part);
    }
}
