package io.javabrains.moviecatalogservice;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java8 {
    public static void main(String[] args) {

        List<String> list1 = Arrays.asList("1","1","4","3","3");
        Map<String, Long> duplicateCount = list1
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println("Result 2 : " + duplicateCount);
        List<Hosting> list = new ArrayList<>();
        list.add(new Hosting(1, "liquidweb.com", 80000));
        list.add(new Hosting(2, "linode.com", 90000));
        list.add(new Hosting(3, "digitalocean.com", 120000));
        list.add(new Hosting(4, "aws.amazon.com", 200000));
        list.add(new Hosting(5, "mkyong.com", 1));
        list.add(new Hosting(6, "linode.com", 100000));

        list.add(new Hosting(7, "linode.com", 50000));

        //example 1
        List<Hosting> result2 = list.stream().sorted(Comparator.comparingLong(Hosting::getWebsites).reversed()).collect(Collectors.toList());
        result2.stream().forEach(hosting -> System.out.println(hosting.getWebsites()));
        System.out.println("Result 2 : " + result2);
        //example 1
        Map result1 = list.stream()
                .sorted(Comparator.comparingLong(Hosting::getWebsites).reversed())
                .collect(
                        Collectors.toMap(
                                Hosting::getName, Hosting::getWebsites, // key = name, value = websites
                                (oldValue, newValue) -> newValue,       // if same key, take the old key
                                LinkedHashMap::new                      // returns a LinkedHashMap, keep order
                        ));

        System.out.println("Result 2 : " + result1);

    }
}

class Hosting {

    private int Id;
    private String name;
    private long websites;

    public Hosting(int id, String name, long websites) {
        Id = id;
        this.name = name;
        this.websites = websites;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public long getWebsites() {
        return websites;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsites(long websites) {
        this.websites = websites;
    }
}
