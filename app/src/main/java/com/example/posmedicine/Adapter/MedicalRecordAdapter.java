package com.example.posmedicine.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.posmedicine.MedicalRecordFragment;
import com.example.posmedicine.models.ComplaintHeader;

import java.util.List;

/**
 * Created by Surya_N2267 on 3/30/2017.
 */

public class MedicalRecordAdapter extends FragmentStatePagerAdapter {
    private List<ComplaintHeader> complaintHeader;

    public MedicalRecordAdapter(FragmentManager fm, List<ComplaintHeader> complaintHeader) {
        super(fm);
        this.complaintHeader = complaintHeader;
    }

    @Override
    public Fragment getItem(int position) {
        return MedicalRecordFragment.newInstance(complaintHeader.get(position));
    }

    @Override
    public int getCount() {
        return complaintHeader.size();
    }
}
