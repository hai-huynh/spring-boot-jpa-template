# spring-boot-jpa-template

## About

This is a template for Spring Boot JPA application. The configuration file is `src/main/resources/application.yml` which
gets parsed by [SnakeYAML](https://code.google.com/p/snakeyaml/). The entry point for the application is `Application`.

## Password Encryption

The database passwords are stored in `application.yml` in encrypted form using [jasypt](http://www.jasypt.org/). The
format is as follows:

```yaml
spring:
  profiles: development

  datasource:
    username: <username>
    password: ENC(<encrypted_base64_password>)
```

The encryption key for protecting the password is contained with {{Application#JASYPT_ENCRYPTION_KEY}}. This key can be
used to encrypt the password using the following command:

```
<jasypt_home>/bin/encrypt.[sh|bat] input="<db_password>" password=<jasypt_enc_key>
```

The output of the command looks like this:

```
$ encrypt.sh input="<db_password>" password=<jasypt_enc_key>

----ENVIRONMENT-----------------

Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 24.65-b04



----ARGUMENTS-------------------

input: <db_password>
password: <jasypt_enc_key>



----OUTPUT----------------------

<base64_encoded_password>
```

Here's an article for reference: [Spring JPA + Jasypt](http://justinrodenbostel.com/2014/06/06/part-5a-additional-credential-security-spring-data-jpa-jasypt/)
