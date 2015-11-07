/*
 * Copyright 2015 NECTEC
 * National Electronics and Computer Technology Center, Thailand
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

package th.or.nectec.android.widget.thai.addresspicker.repository;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import th.or.nectec.domain.thai.address.subdistrict.SubdistrictRepository;
import th.or.nectec.entity.thai.Address;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by N. Choatravee on 5/11/2558.
 */
public class StubSubdistrictRepository implements SubdistrictRepository {

    ArrayList<Address> allSubdistrict = new ArrayList<>();

    public StubSubdistrictRepository(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("subdistrict.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            Gson gson = new Gson();

            reader.beginArray();
            while (reader.hasNext()) {
                Address message = gson.fromJson(reader, Address.class);
                allSubdistrict.add(message);
            }
            reader.endArray();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Address> findByDistrictCode(String districtCode) {
        List<Address> querySubdistrict = new ArrayList<>();
        for (Address eachSubdistrict : allSubdistrict) {
            String queryAddressCode = eachSubdistrict.getAddressCode();
            if (queryAddressCode.startsWith(districtCode)) {
                querySubdistrict.add(eachSubdistrict);
            }
        }
        return querySubdistrict.isEmpty() ? null : querySubdistrict;
    }


}