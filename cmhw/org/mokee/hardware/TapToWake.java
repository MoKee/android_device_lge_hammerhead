/*
 * Copyright (C) 2014 The MoKee OpenSource Project
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

package org.mokee.hardware;

import org.mokee.hardware.util.FileUtils;
import java.io.*;

public class TapToWake {

    private static String CONTROL_PATH = "/sys/android_touch/doubletap2wake";

    public static boolean isSupported() {
        return true;
    }

    public static boolean isEnabled() {
        return FileUtils.readOneLine(CONTROL_PATH).equals("1");
    }

    public static boolean setEnabled(boolean state) {
        String value = state ? "1" : "0";
        try {
            Process p = Runtime.getRuntime().exec("sh");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("echo " + value + " > " + CONTROL_PATH + "\n");
            os.writeBytes("exit\n");
            os.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

