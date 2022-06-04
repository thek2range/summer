package support;


public class DataTableHeaders {
    private String id;
    private long duration;
    private boolean alert;
    private String type;
    private String host;


    public String getId() {
        return this.id;
    }

    public long getDuration() {
        return this.duration;
    }

    public String getType() {
        return this.type;
    }

    public String getHost() {
        return this.host;
    }

    public boolean isAlert() {
        return this.alert;
    }

    public static class Builder {
        private final String id;
        private final long duration;
        private final boolean alert;
        private String type;
        private String host;

        public Builder(String id, long duration, boolean alert) {
            this.id = id;
            this.duration = duration;
            this.alert = alert;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        public DataTableHeaders build() {
            DataTableHeaders data = new DataTableHeaders();
            data.alert = this.alert;
            data.duration = this.duration;
            data.type = this.type;
            data.host = this.host;
            data.id = this.id;
            return data;
        }
    }
}
