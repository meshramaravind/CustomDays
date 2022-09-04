package com.arvind.customdays;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.arvind.customdays.adapter.CustomDaysDynamicTabsAdapter;
import com.arvind.customdays.databinding.ActivityMainBinding;
import com.arvind.customdays.databinding.DialogLayoutDaysBinding;
import com.arvind.customdays.model.DaysModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomDaysDynamicTabsAdapter.OnItemSelectedListener {
    ActivityMainBinding mBinding;

    private Dialog dialogDays;
    CustomDaysDynamicTabsAdapter adapter;
    private DialogLayoutDaysBinding dialogLayoutDaysBinding;
    ArrayList<DaysModel> listDaysTabs = new ArrayList<>();
    private String selectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);

        initialize();
        toolbarInitialize();

    }

    private void toolbarInitialize() {
        mBinding.toolbarTitle.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24));
        mBinding.toolbarTitle.setNavigationOnClickListener(view -> finish());
    }

    private void initialize() {
        mBinding.edSetCustomDaysSelected.setOnClickListener(view -> showCustomDaysDialog());
    }

    private void showCustomDaysDialog() {
        if (dialogDays == null) {
            dialogDays = new Dialog(MainActivity.this);
            dialogLayoutDaysBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(MainActivity.this),
                    R.layout.dialog_layout_days,
                    findViewById(R.id.layout_days_container),
                    false);
            dialogDays.setContentView(dialogLayoutDaysBinding.getRoot());

            dialogLayoutDaysBinding.tvCancelDialog.setOnClickListener(view -> dialogDays.dismiss());

            dialogLayoutDaysBinding.tvApplyDialog.setOnClickListener(view -> {
                mBinding.edSetCustomDaysSelected.setText(selectedValue);
                dialogDays.dismiss();
            });

            addDays();

            dialogLayoutDaysBinding.rvCustomDays.setHasFixedSize(true);
            adapter = new CustomDaysDynamicTabsAdapter(this, listDaysTabs, this);
            dialogLayoutDaysBinding.rvCustomDays.setAdapter(adapter);


            dialogLayoutDaysBinding.edSetCustomDaysDialog.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (s.length() > 0) {
                        selectedValue = dialogLayoutDaysBinding.edSetCustomDaysDialog.getText().toString() + " Days";
                        addDays();
                        adapter.updateList(listDaysTabs);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }
        Window window = dialogDays.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(MATCH_PARENT, WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialogDays.show();
    }

    private void addDays() {
        listDaysTabs = new ArrayList<>();
        listDaysTabs.add(new DaysModel("7 Days", false));
        listDaysTabs.add(new DaysModel("15 Days", false));
        listDaysTabs.add(new DaysModel("30 Days", false));
        listDaysTabs.add(new DaysModel("45 Days", false));
        listDaysTabs.add(new DaysModel("60 Days", false));
        listDaysTabs.add(new DaysModel("90 Days", false));
    }

    @Override
    public void onDaysSelected(int pos, DaysModel daysModel) {
        selectedValue = daysModel.getTextTitle();
        ArrayList<DaysModel> newList = new ArrayList<>();
        for (DaysModel f : listDaysTabs) {
            if (f.getTextTitle().equals(daysModel.getTextTitle())) {
                newList.add(new DaysModel(f.getTextTitle(), true));
            } else {
                newList.add(new DaysModel(f.getTextTitle(), false));
            }
        }
        dialogLayoutDaysBinding.edSetCustomDaysDialog.setText("");
        adapter.updateList(newList);
    }
}