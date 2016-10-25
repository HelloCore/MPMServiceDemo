
package io.github.hellocore.MPMService.Filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GZIPFilter implements Filter {

  public void doFilter(ServletRequest req, ServletResponse res,
      FilterChain chain) throws IOException, ServletException {
	    if (req instanceof HttpServletRequest) {
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;
      String ae = request.getHeader("accept-encoding");
      
      if (ae != null && ae.indexOf("gzip") != -1) {
    	  GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
    	  chain.doFilter(req, wrappedResponse);
    	  wrappedResponse.finishResponse();
      }else{
    	  chain.doFilter(req, res);
      }
    }
  }

  public void init(FilterConfig filterConfig) {
    // noop
  }

  public void destroy() {
    // noop
  }
}
