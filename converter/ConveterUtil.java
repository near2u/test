package converter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConveterUtil<R> {

	static public <T> Map<String, PropertyDetails> object2MapWithPropertyDetails(T t) {
		Class<? extends Object> obj = t.getClass();
		Field[] cfields = obj.getDeclaredFields();
		Map<String, PropertyDetails> map = new HashMap<>();
		for (Field f : cfields) {
			String attributeName = f.getName();
			String getterMethodName = "get" + attributeName.substring(0, 1).toUpperCase()
					+ attributeName.substring(1, attributeName.length());
			Method method = null;
			try {
				method = obj.getMethod(getterMethodName);
				Object valObject = method.invoke(t);
				PropertyDetails details = new PropertyDetails(obj.getDeclaredField(attributeName).getType(), valObject);
				map.put(attributeName, details);
			} catch (Exception e) {
				System.out.println("Unexpected Execption  " + e.getMessage());
				e.printStackTrace();
				continue;
			}
		}
		return map;
	}

	static final BiFunction<Class, String, Method> getMethod = (t, methodName) -> {
		try {
			return t.getMethod(methodName, t);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	};

	static Object applyMethod(Method method, Object target, Object[] obj) {
		try {
			Object invoke = method.invoke(target, obj);
			return invoke;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	static Map<String, Object> convertToObject(Map<String, Object> map, Rules rules) {

		StudentdDto dto = new StudentdDto();
		Map<String, PropertyDetails> ouputObjectDetails = object2MapWithPropertyDetails(dto);
		Map<String, Object> output = new HashMap<>();
		map.entrySet().forEach(data -> {
			if (ouputObjectDetails.containsKey(data.getKey())) {
				Object val = map.get(data.getKey());
				output.put(data.getKey(), val);
			}

		});

		rules.getData().stream().forEach(rule -> {
			String[] fieldName = rule.getFields().split(",");
			PropertyDetails propertyDetails = ouputObjectDetails.get(rule.getName());
			Stream.of(fieldName).forEach(f -> {
				if (propertyDetails.getType().isAssignableFrom(String.class)) {
					concatOp(map, output, rule, propertyDetails, f);
				}

				if (propertyDetails.getType().isAssignableFrom(Integer.class)) {
					sum(map, output, rule, propertyDetails, f);
				}

			});

		});
		return output;
	}

	private static void sum(Map<String, Object> map, Map<String, Object> output, Rule rule,
			PropertyDetails propertyDetails, String f) {
		Integer outputNumber = (Integer) Optional.ofNullable(output.get(rule.getName())).map(val -> val).orElse(0);
		Object inputNumber = Optional.ofNullable(map.get(f)).map(val -> val).orElse(0);
		Method method = null;
		try {
			method = propertyDetails.getType().getMethod(rule.getOp(), int.class, int.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = applyMethod(method, outputNumber, new Object[] { inputNumber, outputNumber });
		output.put(rule.getName(), obj);
	}

	private static void concatOp(Map<String, Object> map, Map<String, Object> output, Rule rule,
			PropertyDetails propertyDetails, String f) {
		String str = (String) Optional.ofNullable(output.get(rule.getName())).map(val -> val).orElse("");
		
		Object otherString = Optional.ofNullable(map.get(f)).map(val -> val).orElse("");
		
		Object obj = applyMethod(getMethod.apply(propertyDetails.getType(), rule.getOp()), str,
				new Object[] { otherString.toString() });
		
		output.put(rule.getName(), obj);
	}

	public static Object convert(Object input, Class output, Rules rules) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(input, Map.class);
		System.out.println(map);
		Map<String, Object> convertToObject = convertToObject(map, rules);

		StudentdDto studentdDto = mapper.convertValue(convertToObject, StudentdDto.class);
		System.out.println(studentdDto);
		return output;
	}

	public static void main(String[] args) {

		Yaml yaml = new Yaml();
		Rules rules = null;
		try (InputStream in = ConveterUtil.class.getResourceAsStream("/application.yml")) {
			rules = yaml.loadAs(in, Rules.class);
			System.out.println(rules);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Student student = new Student();
		student.setAge(10);
		student.setEmail("adc@gmail.com");
		student.setName("xyz");
		student.setLast("pqr");
		student.setDate(new Date());

		convert(student, StudentdDto.class, rules);
	}

}
