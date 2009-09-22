/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.tasks.javadoc

import org.gradle.api.internal.project.IsolatedAntBuilder
import org.gradle.util.BootstrapUtil

/**
 * @author Hans Dockter
 */
class AntGroovydoc {
    void execute(List sourceDirs, File destDir, List<String> packageNames, boolean use, String windowTitle,
        String docTitle, String header, String footer, File overview, boolean includePrivate, IsolatedAntBuilder ant,
        List groovyClasspath) {
        Map args = [:]
        args.sourcepath = sourceDirs.join(File.pathSeparator)
        args.destdir = destDir
        if (packageNames) {
            args.packagenames = packageNames.join(',')
        }
        args.use = use
        args['private'] = includePrivate 
        addToMapIfNotNull(args, 'windowtitle', windowTitle)
        addToMapIfNotNull(args, 'doctitle', docTitle)
        addToMapIfNotNull(args, 'header', header)
        addToMapIfNotNull(args, 'footer', footer)
        addToMapIfNotNull(args, 'overview', overview)

        ant.execute(BootstrapUtil.antJarFiles + groovyClasspath) {
            taskdef(name: 'groovydoc', classname: 'org.codehaus.groovy.ant.Groovydoc')
            groovydoc(args)
        }
    }

    void addToMapIfNotNull(Map map, String key, Object value) {
        if (value != null) map.put(key, value)
    }
}
