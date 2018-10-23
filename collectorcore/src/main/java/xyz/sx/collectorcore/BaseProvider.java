package xyz.sx.collectorcore;

public abstract class BaseProvider {
    private boolean isRunning = false;

    public abstract BaseSensorData getData();

    public void start() {
        if (!isRunning) {
            isRunning = true;
            init();
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            destroy();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    protected void destroy(){}

    protected abstract void init();
}
