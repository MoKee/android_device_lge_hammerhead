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

public class SweepToWake {

    private static String CONTROL_PATH1 = "/sys/android_touch/sweep2wake";
    private static String CONTROL_PATH2 = "/sys/android_touch/s2w_s2sonly";

    public static boolean isSupported() {
        return FileUtils.fileExist(CONTROL_PATH1) &&
                FileUtils.fileExist(CONTROL_PATH2);
    }

    public static int isEnabled() {
        String s2w = FileUtils.readOneLine(CONTROL_PATH1);
        String s2s = FileUtils.readOneLine(CONTROL_PATH2);

        if (s2w.equals("0")) {
            return 0;
        } else if (s2w.equals("1") && s2s.equals("0")) {
            return 1;
        } else {
            return 2;
        }
    }

    public static boolean setEnabled(int type) {
        switch(type) {
            case 0:
                return FileUtils.altWrite(false, CONTROL_PATH1) &&
                        FileUtils.altWrite(false, CONTROL_PATH2);
            case 1:
                return FileUtils.altWrite(true, CONTROL_PATH1) &&
                        FileUtils.altWrite(false, CONTROL_PATH2);
            default:
                return FileUtils.altWrite(true, CONTROL_PATH1) &&
                        FileUtils.altWrite(true, CONTROL_PATH2);
        }
    }
}
