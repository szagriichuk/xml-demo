package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DemoApplication {
    private static final Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) throws JAXBException {

        Random random = new Random(123123);
        JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
        List<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Employee employee = new Employee();
            employee.setId(random.nextInt());
            employee.setFirstName(random.nextInt() + "Name");
            employeeList.add(employee);
        }
        Employees employees = new Employees();
        employees.setEmployees(employeeList);

        String s = asString(jaxbContext, employees);
        String[] strings = s.split("(?<=</employee>)");
        for (String string : strings) {
            log.info(string);
        }

    }

    private static String asString(JAXBContext pContext,
                                   Object pObject)
            throws
            JAXBException {

        java.io.StringWriter sw = new StringWriter();

        Marshaller marshaller = pContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(pObject, sw);


        return sw.toString();
    }
}
