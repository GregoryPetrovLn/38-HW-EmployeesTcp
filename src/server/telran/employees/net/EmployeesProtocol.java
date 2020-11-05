package server.telran.employees.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import common.telran.employees.dto.*;
import common.telran.employees.services.interfaces.EmployeeService;
import common.telran.net.*;

import server.telran.net.server.Protocol;

import static common.telran.employees.api.ApiConstants.*;

public class EmployeesProtocol implements Protocol {
	static EmployeeService service;
	static Map<String, Function<Serializable, Response>> mapFn;
	static {
		mapFn = new HashMap<>();
		mapFn.put(GENERATE_EMPLOYEES, EmployeesProtocol::generateEmployees);
		mapFn.put(SAVE_TO_FILES, EmployeesProtocol::saveToFiles );
		mapFn.put(LOAD_FROM_FILES, EmployeesProtocol::loadFromFiles);

		mapFn.put(POST_EMPLOYEE, EmployeesProtocol::addEmployee);
		mapFn.put(GET_EMPLOYEE, EmployeesProtocol::getEmployee);
		mapFn.put(DELETE_EMPLOYEE, EmployeesProtocol::removeEmployee);
		mapFn.put(PUT_EMPLOYEE, EmployeesProtocol::updateEmployee);

		mapFn.put(GET_EMPLOYEES_BY_SALARIES_INTERVAL, EmployeesProtocol::minMaxSalaryEmployees);
		mapFn.put(GET_DEPARTMENT_AVG_SALARY, EmployeesProtocol::getDepartmentAvgSalaryDistribution);

		mapFn.put(GET_EMPLOYEES_BY_AGE, EmployeesProtocol::getEmployeesByAge);
		mapFn.put(GET_EMPLOYEES_BY_DEPARTMENT, EmployeesProtocol::getEmployeesByDepartment);
		mapFn.put(GET_EMPLOYEES_BY_SALARY, EmployeesProtocol::getEmployeeBySalary);
	}

	public EmployeesProtocol(EmployeeService service) {
		super();
		EmployeesProtocol.service = service;
	}

	@Override
	public Response getResponse(Request request) {

		return mapFn
				.getOrDefault(request.requestType,
						r -> new Response(TcpResponseCode.WRONG_REUEST, "wrong request type"))
				.apply(request.requestData);
	}

	static Response addEmployee(Serializable requestData) {
		try {
			Employee empl = (Employee) requestData;
			ReturnCodes res = service.addEmployee(empl);
			return new Response(TcpResponseCode.OK, res);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}

	static Response getEmployee(Serializable requestData) {
		try {
			Long id = (Long) requestData;
			Employee empl = service.getEmployee(id);
			return new Response(TcpResponseCode.OK, empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}

	static Response removeEmployee(Serializable requestData) {
		try {
			Long id = (Long) requestData;
			ReturnCodes empl = service.removeEmployee(id);
			return new Response(TcpResponseCode.OK, empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}

	static Response updateEmployee(Serializable requestData) {
		try {
			Object[] data = (Object[]) requestData;
			Employee empl = service.updateEmployee((Long) data[0], (Employee) data[1]);
			return new Response(TcpResponseCode.OK, empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}

	static Response generateEmployees(Serializable requestData) {
		try {
			Object[] data = (Object[]) requestData;
			ReturnCodes empl = service.generateRandomEmployees((Integer) data[0], (Integer) data[1], (Integer) data[2],
					GeneratorMode.RANDOM);
			return new Response(TcpResponseCode.OK, empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}

	static Response getStatisticsOperationsItem(Serializable requestData) {
		try {
			Object[] data = (Object[]) requestData;
			ReturnCodes empl = service.generateRandomEmployees((Integer) data[0], (Integer) data[1], (Integer) data[2],
					GeneratorMode.RANDOM);
			return new Response(TcpResponseCode.OK, empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}

	static Response getDepartmentAvgSalaryDistribution(Serializable requestData) {
		try {
			DepartmentSalary[] empl = service.getDepartmentAvgSalaryDistribution();
			return new Response(TcpResponseCode.OK, empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}

	static Response minMaxSalaryEmployees(Serializable requestData) {
		try {	
			MinMaxSalaryEmployees[] empl = service.getEmployeesBySalariesInterval((Integer)requestData );
			return new Response(TcpResponseCode.OK, empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}
	
	static Response getEmployeesByAge(Serializable requestData) {
		try {	
			Object[] data = (Object[]) requestData;
			Iterable<Employee> empl = service.getEmployeesByAge((Integer)data[0],(Integer)data[1] );
			return new Response(TcpResponseCode.OK, (Serializable) empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}
	static Response getEmployeesByDepartment(Serializable requestData) {
		try {	
			Iterable<Employee> empl = service.getEmployeesByDepartment((String) requestData);
			return new Response(TcpResponseCode.OK, (Serializable) empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}
	static Response getEmployeeBySalary(Serializable requestData) {
		try {	
			Object[] data = (Object[]) requestData;
			Iterable<Employee> empl = service.getEmployeesBySalary((Integer)data[0],(Integer)data[1] );
			return new Response(TcpResponseCode.OK, (Serializable) empl);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}
	static Response saveToFiles(Serializable requestData) {
		try {	
			ReturnCodes code =  service.saveToFiles();
			return new Response(TcpResponseCode.OK,  code);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}
	static Response loadFromFiles(Serializable requestData) {
		try {	
			ReturnCodes code =  service.loadFromFiles();
			return new Response(TcpResponseCode.OK, (Serializable) code);
		} catch (Exception e) {
			return new Response(TcpResponseCode.WRONG_REUEST, e.getMessage());
		}
	}


}
