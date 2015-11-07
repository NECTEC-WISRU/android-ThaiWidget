/*
 * Copyright (c) 2015 NECTEC
 *   National Electronics and Computer Technology Center, Thailand
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package th.or.nectec.entity.thai;

import th.or.nectec.util.TextUtils;

public class Province {

    private String code;
    private String name;
    private Region region;


    public Province(String code, String name, Region region) {
        this.region = region;
        setCode(code);
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if( code.length() == 2 && TextUtils.isDigitOnly(code))
            this.code = code;
        else
            throw new InvalidCodeFormatException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public class InvalidCodeFormatException extends RuntimeException {
    }
}
