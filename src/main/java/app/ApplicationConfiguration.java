package app;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class ApplicationConfiguration
{

    public static final String JASYPT_ENCRYPTION_KEY = "<SUPPLY_AN_ENCRYPTION_KEY>";

    @Value("${spring.datasource.driverClassName}")
    private String databaseDriverClassName;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value( "${spring.datasource.validationQuery}" )
    private String validationQuery;

    @Value( "${spring.datasource.validationInterval}" )
    private int validationInterval;

    @Bean
    public DataSource datasource()
            throws IOException
    {
        final org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(databaseDriverClassName);
        ds.setUrl(datasourceUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(getSecurePassword());
        ds.setInitialSize(initialSize);
        ds.setMaxActive(maxActive);

        // Set for reconnect.
        ds.setValidationQuery(validationQuery);
        ds.setTestOnBorrow(true);
        ds.setValidationInterval(validationInterval);

        return ds;
    }

    private String getSecurePassword()
            throws IOException
    {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(JASYPT_ENCRYPTION_KEY);
        final String encryptedPassword = databasePassword.substring(4, databasePassword.length() - 1);
        return encryptor.decrypt(encryptedPassword);
    }

}
