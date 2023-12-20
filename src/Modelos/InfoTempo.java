package Modelos;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InfoTempo {
    private double temp;
    private int max;
    private int min;
    private transient LocalDate date;
    private transient LocalTime time;
    private String description;
    private String city;

    private String dateString;
    private String timeString;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.dateString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDate(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            this.date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.dateString = dateString;
        } else {
            this.date = LocalDate.now();
            this.dateString = this.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
        this.timeString = time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String conditionSlug) {
        this.description = conditionSlug;

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateString() {
        return dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTime(String timeString) {
        if (timeString != null && !timeString.isEmpty()) {
            this.time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
            this.timeString = timeString;
        } else {
            this.time = LocalTime.now();
            this.timeString = this.time.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

    @Override
    public String toString() {
        return "InfoTempo{" +
                "temp=" + temp +
                ", max=" + max +
                ", min=" + min +
                ", date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", dateString='" + dateString + '\'' +
                ", timeString='" + timeString + '\'' +
                '}';
    }

    public static class InfoTempoDeserializer implements JsonDeserializer<InfoTempo> {
        private JsonObject results;
        @Override
        public InfoTempo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            InfoTempo infoTempo = new InfoTempo();

            if (jsonObject.has("results")) {
                JsonObject results = jsonObject.getAsJsonObject("results");
                infoTempo.setTemp(results.get("temp").getAsDouble());
                infoTempo.setDescription(results.get("description").getAsString());

                JsonArray forecastArray = results.getAsJsonArray("forecast");
                if (forecastArray.size() > 0) {
                    JsonObject firstForecast = forecastArray.get(0).getAsJsonObject();
                    infoTempo.setMax(firstForecast.get("max").getAsInt());
                    infoTempo.setMin(firstForecast.get("min").getAsInt());
                }


                if (results.has("city")) {
                    String cityInfo = results.get("city").getAsString();
                    infoTempo.setCity(cityInfo);
                }
            }

            return infoTempo;
        }
    }


}
