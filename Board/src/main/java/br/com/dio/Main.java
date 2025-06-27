package br.com.dio;

import br.com.dio.persistence.migration.MigrationStrategy;



import static br.com.dio.persistence.config.ConnectionConfig.getConnection;

public class Main {
    public static void main(String[] args) {
       try(var connection = getConnection()){
           new MigrationStrategy(connection).executeMigration();
       }catch (Exception ex){
           ex.printStackTrace();
       }
    }
}