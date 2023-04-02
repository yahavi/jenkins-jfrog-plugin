package io.jenkins.plugins.jfrog;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.model.TaskListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author yahavi
 **/
public class JenkinsJFrogLog extends PrintStream implements TaskListener {
    private final ByteArrayOutputStream output;

    public JenkinsJFrogLog(TaskListener taskListener) {
        super(taskListener.getLogger());
        this.output = new ByteArrayOutputStream();
    }

    public String getOutput() {
        return output.toString();
    }

    @NonNull
    @Override
    public PrintStream getLogger() {
        return this;
    }

    /**
     * override write(). This is the actual "tee" operation.
     */
    public void write(int x) {
        super.write(x);
        output.write(x);
    }

    /**
     * override write(). This is the actual "tee" operation.
     */
    public void write(byte[] x, int o, int l) {
        super.write(x, o, l);
        output.write(x, o, l);
    }

    /**
     * Close both streams.
     */
    public void close() {
        try {
            output.close();
        } catch (IOException e) {
            // Ignore
        }
        super.close();
    }

    /**
     * Flush both streams.
     */
    public void flush() {
        try {
            output.flush();
        } catch (IOException e) {
            // Ignore
        }
        super.flush();
    }
}
