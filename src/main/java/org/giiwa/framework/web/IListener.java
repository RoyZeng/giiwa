/*
 * Copyright 2015 Giiwa, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.giiwa.framework.web;

import org.apache.commons.configuration.Configuration;

/**
 * the life listener which will be invoked when the system "start", "stop" <br>
 * the life listener is configured in module.xml
 * 
 * <pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;module version="1.0"&gt;
  ...
  &lt;listener&gt;
    &lt;class&gt;org.giiwa.app.web.DefaultListener&lt;/class&gt;
  &lt;/listener&gt;
 ...
&lt;/module&gt;
 * </pre>
 * 
 * @author yjiang
 * 
 */
public interface IListener {

  /**
   * Upgrade.
   * 
   * @param conf
   *          the conf
   * @param module
   *          the module
   */
  void upgrade(Configuration conf, Module module);

  /**
   * Uninstall.
   * 
   * @param conf
   *          the conf
   * @param module
   *          the module
   */
  void uninstall(Configuration conf, Module module);

  /**
   * On start.
   * 
   * @param conf
   *          the conf
   * @param module
   *          the module
   */
  void onStart(Configuration conf, Module module);

  /**
   * On stop.
   */
  void onStop();

}
