import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class WeatherApp {
    static ArrayList<String> location = new ArrayList<>();
    public static String getdata() throws IOException {
        // Check Location Match
        try {
            URL weather_api = new URL("https://api.weatherapi.com/v1/current.json?key=75c6804d03444182b6015343223003&q=" + location.get(0) +"&aqi=no");
            URLConnection connect = weather_api.openConnection();
            BufferedReader write = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        } catch (IOException e) {
            System.out.println("No City/Province Match!");
            System.exit(400);
        }
        // DATA SOURCE
        URL weather_api = new URL("https://api.weatherapi.com/v1/current.json?key=75c6804d03444182b6015343223003&q=" + location.get(0) +"&aqi=no");
        // GET JSON DATA VIA URL
        URLConnection connect = weather_api.openConnection();
        BufferedReader write = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        // READ DATA
        String result = write.readLine();
        return result;
    }
    public static String weather()throws IOException, ParseException{
        JSONObject weather_json = (JSONObject) new JSONParser().parse(getdata());
        // ARRAY
        Map location_array = ((Map) weather_json.get("location"));
        Map current_array = ((Map) weather_json.get("current"));
        Map condition_array = ((Map) current_array.get("condition"));
        //
        String get_country = (String) location_array.get("country");
        String get_province = (String) location_array.get("region");
        String get_city = (String) location_array.get("name");
        //
        Double get_wind_kph = (Double) current_array.get("wind_kph");
        String get_forcast = (String) condition_array.get("text");
        Double get_temp = (Double) current_array.get("temp_c");
        Double get_feels_like = (Double) current_array.get("feelslike_c");
        String get_last_update = (String) current_array.get("last_updated");
        String get_local_time = (String) location_array.get("tz_id");
        //
        String location_data = "Country: " + get_country + "\nCity: " + get_city + "\nProvince: " + get_province + "\n";
        String weather_data = "Forecast: " + get_forcast + "\nWind Speed: " + get_wind_kph + " kph" +
        "\nTemperature: " + get_temp + " C" + "\nFeels Like: " + get_feels_like + " C" + "\nLast Updated: " + get_last_update +
                " " + get_local_time;
        return location_data + weather_data;
    }
    public static void main(String[] args) throws IOException, ParseException {
    // START PROGRAM
        Scanner get_user = new Scanner(System.in);
        System.out.println("Choose location\n1. General Trias\n2. Manila\n3. Type City/Province/Postal Code");
        int user_choice = get_user.nextInt();
            switch (user_choice){
                case 1:
                    location.add("General%20Trias");
                    break;
                case 2:
                    location.add("Manila");
                    break;
                case 3:
                    Scanner get_custom = new Scanner(System.in);
                    System.out.println("Enter City/Province/Postal Code");
                    String get_result = get_custom.nextLine();
                    String get_final = get_result.replace(" ","%20");
                    location.add(get_final);
                    break;
                default:
                    System.out.println("Invalid Selection");
                    System.exit(1);
            }
        System.out.println(weather());
    }
}
