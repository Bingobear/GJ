

import com.google.gson.Gson;


public class GSON_Main {
	public GSON_Main() {

	}
	public static void main(String[] args) {
		Test_Object obj = new Test_Object();
		/*with white spaces
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(someObject);*/
		Gson gsona = new Gson();
		String json = gsona.toJson(obj);  
		Gson gson = new Gson();
		gson.toJson(1);            
		gson.toJson("abcd");      
		gson.toJson(new Long(10)); 
		int[] values = { 1 };
		gson.toJson(values); 
		System.out.println(json);

		}
/*Custom Serialization and Deserialization
Sometimes default representation is not what you want. This is often the case when dealing with library classes (DateTime, etc).
Gson allows you to register your own custom serializers and deserializers. This is done by defining two parts:

    Json Serialiers: Need to define custom serialization for an object
    Json Deserializers: Needed to define custom deserialization for a type
    Instance Creators: Not needed if no-args constructor is available or a deserializer is registered

GsonBuilder gson = new GsonBuilder();
gson.registerTypeAdapter(MyType2.class, new MyTypeAdapter());
gson.registerTypeAdapter(MyType.class, new MySerializer());
gson.registerTypeAdapter(MyType.class, new MyDeserializer());
gson.registerTypeAdapter(MyType.class, new MyInstanceCreator());

registerTypeAdapter call checks if the type adapter implements more than one of these interfaces and register it for all of them.
Writing a Serializer
Here is an example of how to write a custom serializer for JodaTime DateTime class.

private class DateTimeSerializer implements JsonSerializer<DateTime> {
  public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toString());
  }
}*/

}
