/*
 * Copyright 2015 JIHU, Inc. and/or its affiliates.
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
package org.giiwa.app.web.admin;

import java.io.File;
import org.giiwa.core.bean.X;
import org.giiwa.core.json.JSON;
import org.giiwa.framework.bean.OpLog;
import org.giiwa.framework.bean.Temp;
import org.giiwa.framework.web.*;

/**
 * web api: /admin/log <br>
 * used to manage oplog,<br>
 * required "access.logs.admin"
 * 
 * @author joe
 *
 */
public class logs extends Model {

  @Path(path = "download", login = true, access = "access.logs.admin")
  public void download() {

    JSON jo = JSON.create();
    String f = this.getString("f");

    File f0 = new File(Model.GIIWA_HOME + "/logs/");
    File f1 = new File(Model.GIIWA_HOME + "/logs/" + f);
    try {
      if (f1.getCanonicalPath().startsWith(f0.getCanonicalPath())) {

        Temp t = Temp.create(f + ".zip");
        t.zipcopy(f1.getName(), f1);

        jo.put(X.STATE, 200);
        jo.put("src", t.getUri());
      } else {
        jo.put(X.MESSAGE, "not found, name=" + f);
        jo.put(X.STATE, 201);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      jo.put(X.MESSAGE, e.getMessage());
      jo.put(X.STATE, 201);
    }

    this.response(jo);
  }

  /**
   * Delete.
   */
  @Path(path = "delete", login = true, access = "access.logs.admin")
  public void delete() {
    JSON jo = new JSON();
    String f = this.getString("f");

    File f1 = new File(Model.GIIWA_HOME + "/logs/" + f);
    try {
      if (f1.getCanonicalPath().startsWith(new File(Model.GIIWA_HOME + "/logs/").getCanonicalPath()) && f1.delete()) {
        OpLog.warn(logs.class, "delete", "deleted=" + f, login, this.getRemoteHost());
        jo.put(X.STATE, 200);
      } else {
        jo.put(X.STATE, 201);
        jo.put(X.MESSAGE, "file not exists");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      jo.put(X.STATE, 201);
      jo.put(X.MESSAGE, e.getMessage());
    }
    this.response(jo);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.giiwa.framework.web.Model#onGet()
   */
  @Path(login = true, access = "access.logs.admin")
  public void onGet() {
    File f = new File(Model.GIIWA_HOME + "/logs");
    File[] ff = f.listFiles();
    this.set("list", ff);

    this.show("/admin/logs.index.html");
  }

}
