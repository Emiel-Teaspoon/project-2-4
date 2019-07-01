package com.example.project24;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CreateEventFragment extends DialogFragment  {

    Calendar date;

    private static final String TAG = MainActivity.class.getSimpleName();
    // Controls for the event creation popup
    private View popupWindowView;
    private TextInputLayout eventTitle;
    private TextInputLayout eventDesc;
    private TextInputLayout eventStart;
    private TextInputLayout eventEnd;
    private Button eventCancelButton;
    private Button eventCreateButton;

    public CreateEventFragment(){}

    public static CreateEventFragment newInstance(LatLng latLng) {
        CreateEventFragment frag = new CreateEventFragment();
        Bundle args = new Bundle();
        args.putParcelable("latLng", latLng);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LatLng latLng = getArguments().getParcelable("latLng");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle("Create Event");
        dialogBuilder.setCancelable(false);

        initPopUpViewControls();
        dialogBuilder.setView(popupWindowView);
        final AlertDialog dialog = dialogBuilder.create();
        initPopupOnClickListeners(dialog, latLng);
        return dialog;
    }


    private void initPopUpViewControls() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        popupWindowView = inflater.inflate(R.layout.fragment_create_event, null);

        eventTitle = popupWindowView.findViewById(R.id.eventTitleText);
        eventDesc = popupWindowView.findViewById(R.id.eventDescText);
        eventStart = popupWindowView.findViewById(R.id.eventStartTimeText);
        eventEnd = popupWindowView.findViewById(R.id.eventEndTimeText);

        eventCancelButton = popupWindowView.findViewById(R.id.eventPopupCancel);
        eventCreateButton = popupWindowView.findViewById(R.id.eventCreate);
    }

    private boolean verifyEventInput() {
        boolean valid = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:SS");

        if (TextUtils.isEmpty(eventTitle.getEditText().getText().toString())) {
            eventTitle.setError("No event title");
            valid = false;
        }
        if (TextUtils.isEmpty(eventDesc.getEditText().getText().toString())) {
            eventDesc.setError("No event description");
            valid = false;
        }
        if (TextUtils.isEmpty(eventStart.getEditText().getText().toString())) {
            eventStart.setError("No start time and date");
            valid = false;
        }
        if (TextUtils.isEmpty(eventEnd.getEditText().getText().toString())) {
            eventEnd.setError("No end time and date");
            valid = false;
        }
        if (!TextUtils.isEmpty(eventStart.getEditText().getText().toString())) {
            try {
                format.parse(eventStart.getEditText().getText().toString());
            } catch (ParseException e) {
                eventEnd.setError("No valid start time or date");
                valid = false;
            }
        }

        if (!TextUtils.isEmpty(eventEnd.getEditText().getText().toString())) {
            try {
                format.parse(eventEnd.getEditText().getText().toString());
            } catch (ParseException e) {
                eventEnd.setError("No valid end time or date");
                valid = false;
            }
        }
        return valid;
    }
    private void initPopupOnClickListeners(final AlertDialog dialog, final LatLng latLng) {
        // Takes data from input controls and sends the new event to the backend.
        eventCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifyEventInput()){
                    return;
                }
                String title = eventTitle.getEditText().getText().toString();
                String desc = eventDesc.getEditText().getText().toString();
                String startDT = eventStart.getEditText().getText().toString();
                String endDT = eventEnd.getEditText().getText().toString();

                ApiClient.createEvent(getContext(), title, desc, latLng.latitude, latLng.longitude, startDT, endDT, MainActivity.app.getUser_id(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Event Create Test", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Event create error", error.toString());
                    }
                });
                dialog.cancel();
                Log.d("closeFrame ",""+ latLng );
            }
        });

        // Close the event creation window.
        eventCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        eventStart.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(1);

            }
        });
        eventEnd.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(2);

            }
        });
    }


    public void showDateTimePicker(final int number) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String selectedDate = dateformat.format(date.getTime());
                        if (number == 1) {
                            eventStart.getEditText().setText(selectedDate);
                            Log.v(TAG, "The choosen one " + date.getTime());
                        }else {
                            eventEnd.getEditText().setText(selectedDate);
                            Log.v(TAG, "The choosen one " + date.getTime());
                        }
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }
}
