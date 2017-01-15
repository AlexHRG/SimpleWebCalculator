

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SrvltCalculator")
public class SrvltCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringA = request.getParameter("a");
		String stringB = request.getParameter("b");
		String operator = request.getParameter("op");
		
		double valueA = 0.0;
		double valueB = 0.0;
		
		boolean noError = true;
		
		try {
			valueA = Double.valueOf(stringA);
			valueB = Double.valueOf(stringB);
		} catch (Exception e) {
			noError = false;
		}
		
		if (noError) {
			double result = 0.0;
			
			try {
				switch (operator) {
				case "+":
					result = functionSum(valueA, valueB);
					break;
				case "-":
					result = functionDif(valueA, valueB);
					break;
				case "*":
					result = functionMul(valueA, valueB);
					break;
				case "/":
					if(valueB != 0.0){
						result = functionDiv(valueA, valueB);
					} else {
						noError = false;
					}
					break;

				default:
					noError = false;
					break;
				}
			} catch (Exception e) {
				noError = false;
			}
			
			if (noError) {
				doSetResult(response, result);
			} else {
				doSetError(response);
			}
		}
	}
	
	protected void doSetResult( HttpServletResponse response, double result ) throws UnsupportedEncodingException, IOException {
		String reply = "{\"error\":0,\"result\":" + Double.toString(result) + "}";
		response.getOutputStream().write( reply.getBytes("UTF-8") );
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus( HttpServletResponse.SC_OK );
	}	

	protected void doSetError( HttpServletResponse response ) throws UnsupportedEncodingException, IOException {
		String reply = "{\"error\":1}";
		response.getOutputStream().write( reply.getBytes("UTF-8") );
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus( HttpServletResponse.SC_OK );
	}
	
	protected double functionSum( double a, double b ) {
		return a + b;
	}

	protected double functionDif( double a, double b ) {
		return a - b;
	}

	protected double functionMul( double a, double b ) {
		return a * b;
	}

	protected double functionDiv( double a, double b ) {
		return a / b;
	}
	
}
