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

package th.or.nectec.android.widget.thai.address;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;
import th.or.nectec.android.widget.thai.OnAddressChangedListener;
import th.or.nectec.android.widget.thai.R;
import th.or.nectec.domain.thai.ThaiAddressPrinter;
import th.or.nectec.domain.thai.address.AddressController;
import th.or.nectec.domain.thai.address.AddressPresenter;
import th.or.nectec.entity.thai.Address;

public class AddressPickerHandler implements OnAddressChangedListener, AddressPresenter {
    private Context context;
    private Activity activity;
    private AddressPickerDialogFragment addressPickerDialogFragment;
    private OnAddressChangedListener onAddressChangedListener;
    private AddressController addressController;
    private TextView textView;
    private Address address;

    public AddressPickerHandler(TextView view, Context context) {
        this.textView = view;
        this.context = context;
        init();
    }

    public void init() {
        if (this.context instanceof Activity) {
            activity = (Activity) this.context;
        }

        if (activity == null)
            return;

        FragmentManager fragmentManager = activity.getFragmentManager();
        AddressPickerDialogFragment addressPickerDialogFragment = (AddressPickerDialogFragment) fragmentManager.findFragmentByTag(AddressPickerDialogFragment.FRAGMENT_TAG);

        if (addressPickerDialogFragment != null) {
            this.addressPickerDialogFragment = addressPickerDialogFragment;
        } else {
            this.addressPickerDialogFragment = new AddressPickerDialogFragment();
        }

        this.addressPickerDialogFragment.setOnAddressChangedListener(this);

        addressController = new AddressController(InMemoryJsonSubdistrictRepository.getInstance(context), new InMemoryJsonDistrictRepository(context), new InMemoryJsonProvinceRepository(context), this);
        textView.setText(R.string.please_define_address);
    }

    @Override
    public void displayAddressInfo(Address address) {
        retrieveAddress(address);
    }

    @Override
    public void alertAddressNotFound() {
        Toast.makeText(context, R.string.address_not_found, Toast.LENGTH_LONG).show();
    }

    private void retrieveAddress(Address address) {
        this.address = address;
        textView.setText(ThaiAddressPrinter.buildShortAddress(address.getSubdistrict().getName(), address.getDistrict().getName(), address.getProvince().getName()));
        if (onAddressChangedListener != null)
            onAddressChangedListener.onAddressChanged(address);
    }

    public boolean onClick() {
        boolean handle = false;
        if (this.addressPickerDialogFragment != null) {
            FragmentManager fm = activity.getFragmentManager();

            if (!this.addressPickerDialogFragment.isAdded()) {
                this.addressPickerDialogFragment.show(fm, AddressPickerDialogFragment.FRAGMENT_TAG);
                handle = true;
                if (address != null) {
                    this.addressPickerDialogFragment.restoreAddressField(address);
                }
            }
        }
        return handle;
    }

    public AddressSavedState buildSaveState(Parcelable parcelable) {
        AddressSavedState savedState = new AddressSavedState(parcelable);
        savedState.addressCode = address == null ? null : address.getSubdistrictCode();
        return savedState;
    }


    public void setAddressCode(String addressCode) {
        addressController.showByAddressCode(addressCode);
    }

    public void setAddress(String subdistrict, String district, String province) {
        addressController.showByAddressInfo(subdistrict, district, province);
    }

    @Override
    public void onAddressChanged(Address address) {
        retrieveAddress(address);
    }

    @Override
    public void onAddressCanceled() {
        if (onAddressChangedListener != null)
            onAddressChangedListener.onAddressCanceled();
    }

    public void setOnAddressChangedListener(OnAddressChangedListener onAddressChangedListener) {
        this.onAddressChangedListener = onAddressChangedListener;
    }

    public Address getAddress() {
        return address;
    }
}