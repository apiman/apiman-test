package io.apiman.test.integration.db.commands
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

def existingProperty = systemProperties.property.find { it.'@name' == propertyName }

def propertyDefiniton = {
    property(name: propertyName, value: value)
}

if ( existingProperty && !replaceExisting ) {
    throw new IllegalStateException("System Property $propertyName already exists in configuration. Define different name or set parameter 'replaceExisting' to true.")
} else
if ( existingProperty ) {
    existingProperty.replaceNode propertyDefiniton
} else {
    systemProperties.appendNode propertyDefiniton
}
