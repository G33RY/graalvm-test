package com.testing;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/HelloWorld")
public class HelloWorldServlet extends HttpServlet {
    static String PAGE_HEADER =
            "<html><head><title>helloworld</title></head><body>";

    static String PAGE_FOOTER = "</body></html>";

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println(PAGE_HEADER);

        Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .option("js.ecmascript-version", "2015")
                .build();

        Value v = context.eval("js", "1 + 3");
        System.out.println(v);
        context.close();

        writer.println("""
                <h1>Hello, World!</h1>
                <h2>Result: %s</h2>
            """.formatted(v.asInt()));
        writer.println(PAGE_FOOTER);
        writer.close();



    }
}
