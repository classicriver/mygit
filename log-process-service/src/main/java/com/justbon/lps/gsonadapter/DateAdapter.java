package com.justbon.lps.gsonadapter;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class DateAdapter extends TypeAdapter<Long>{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void write(JsonWriter jsonWriter, Long date) throws IOException {
        jsonWriter.value(sdf.format(date));
    }

    @Override
    public Long read(JsonReader in) throws IOException {
        if(in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            long date = this.deserializeToDate(in.nextString());
            return date;
        }
    }

    private long deserializeToDate(String s) {
        synchronized (sdf) {
            try {
                return sdf.parse(s).getTime();
            } catch (ParseException ignored) {}
            try {
                return sdf.parse(s).getTime();
            } catch (ParseException ignored) {}
            try {
                return ISO8601Utils.parse(s, new ParsePosition(0)).getTime();
            } catch (ParseException e) {
                throw new JsonSyntaxException(s, e);
            }
        }
    }
}
