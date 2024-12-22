package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class Main {
    public static void main(String[] args) {
        List<Map<String, Object>> data = loadData();

        // 1. Implement various lambdas
        // 1.1. Create at least 6 lambdas
        Predicate<Map<String, Object>> isYoungerThan30 = person -> (int) person.get("age") < 30;
        Function<Map<String, Object>, String> getJob = person -> (String) person.get("job");
        Consumer<Map<String, Object>> printPerson = person -> System.out.println(person);
        Supplier<Map<String, Object>> randomPerson = () -> data.get(new Random().nextInt(data.size()));
        UnaryOperator<Map<String, Object>> increaseSalary = person -> {
            person.put("salary", (int) person.get("salary") + 1000);
            return person;
        };
        BinaryOperator<Map<String, Object>> combineSalaries = (person1, person2) -> {
            person1.put("salary", (int) person1.get("salary") + (int) person2.get("salary"));
            return person1;
        };

        // 1.3. Call each lambda with test data: two calls for each lambda
        System.out.println(isYoungerThan30.test(data.get(0)));
        System.out.println(isYoungerThan30.test(data.get(1)));
        System.out.println(getJob.apply(data.get(0)));
        System.out.println(getJob.apply(data.get(1)));
        printPerson.accept(data.get(0));
        printPerson.accept(data.get(1));
        System.out.println(randomPerson.get());
        System.out.println(randomPerson.get());
        System.out.println(increaseSalary.apply(data.get(0)));
        System.out.println(increaseSalary.apply(data.get(1)));
        System.out.println(combineSalaries.apply(data.get(0), data.get(1)));
        System.out.println(combineSalaries.apply(data.get(2), data.get(3)));

        // 2. Apply Stream API to solve tasks on the attached dataset
        // 2.1. Simple operations
        // 2.1.1. Filtering by an arbitrary predicate
        List<Map<String, Object>> filtered = data.stream()
            .filter(person -> (int) person.get("salary") > 100000)
            .collect(Collectors.toList());

        // 2.1.2. Sorting by an arbitrary field
        List<Map<String, Object>> sorted = data.stream()
            .sorted(Comparator.comparing(person -> (int) person.get("age")))
            .collect(Collectors.toList());

        // 2.1.3. Limiting the number of displayed objects
        List<Map<String, Object>> limited = data.stream()
            .limit(10)
            .collect(Collectors.toList());

        // 2.1.4. Transforming an object into another object
        List<String> jobs = data.stream()
            .map(person -> (String) person.get("job"))
            .collect(Collectors.toList());

        // 2.2. Combining simple operations
        // 2.2.1. Top-10 highest salaries among respondents younger than 25 in city X
        String cityX = "X";
        List<Map<String, Object>> top10Salaries = data.stream()
            .filter(person -> (int) person.get("age") < 25 && person.get("city").equals(cityX))
            .sorted(Comparator.comparing(person -> -(int) person.get("salary")))
            .limit(10)
            .collect(Collectors.toList());

        // 2.2.2. Number of respondents earning more than 50k by profession X
        String professionX = "X";
        long count = data.stream()
            .filter(person -> (int) person.get("salary") > 50000 && person.get("job").equals(professionX))
            .count();

        // 2.2.3. Maximum salary among respondents from city X, aged between A and B
        int A = 20;
        int B = 30;
        Optional<Map<String, Object>> maxSalary = data.stream()
            .filter(person -> person.get("city").equals(cityX) && (int) person.get("age") >= A && (int) person.get("age") <= B)
            .max(Comparator.comparing(person -> (int) person.get("salary")));

        // 2.2.4. Minimum age among respondents from city X earning more than 100k
        Optional<Map<String, Object>> minAge = data.stream()
            .filter(person -> person.get("city").equals(cityX) && (int) person.get("salary") > 100000)
            .min(Comparator.comparing(person -> (int) person.get("age")));

        // 2.3. Grouping
        // 2.3.1. Group all data by an arbitrary string field
        Map<String, List<Map<String, Object>>> groupedByJob = data.stream()
            .collect(Collectors.groupingBy(person -> (String) person.get("job")));

        // 2.3.2. Group all data by an arbitrary string field, counting the number of elements in each group
        Map<String, Long> countByJob = data.stream()
            .collect(Collectors.groupingBy(person -> (String) person.get("job"), Collectors.counting()));

        // 2.3.3. Group all data by cities, counting the maximum salary received in each
        Map<String, Optional<Map<String, Object>>> maxSalaryByCity = data.stream()
            .collect(Collectors.groupingBy(person -> (String) person.get("city"), Collectors.maxBy(Comparator.comparing(person -> (int) person.get("salary")))));

        // 2.3.4. Group all data by cities, calculating the average salary for each profession
        Map<String, Map<String, Double>> avgSalaryByCityAndJob = data.stream()
            .collect(Collectors.groupingBy(person -> (String) person.get("city"), Collectors.groupingBy(person -> (String) person.get("job"), Collectors.averagingInt(person -> (int) person.get("salary")))));
    }

    public static List<Map<String, Object>> loadData() {
        ObjectMapper mapper = new ObjectMapper();
        Path path = Paths.get("src/main/resources/data.json");
        try {
            if (!Files.exists(path)) {
                throw new IOException("File not found: " + path.toString());
            }
            return mapper.readValue(Files.readAllBytes(path), new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
