package net.thumbtack.school.concert.server;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.google.gson.Gson;

import net.thumbtack.school.concert.exception.ServerErrorCode;
import net.thumbtack.school.concert.exception.ServerException;




public class TestServer {
    @TempDir
    public Path tempDir;
    
    //@Ignore
    @Test
    public void testStartStopServerDefault() {
    	Server server = new Server();
    	try {
			server.startServer(null);
			//server.startServer(" .");
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.CONFIG_FILE_NOT_WRITED, e.getServerErrorCode());
		}
    	try {
			server.stopServer(null);
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.OTHER_ERROR, e.getServerErrorCode());
		}
    	

    }
    
       @Test
    public void testRegisterUser() {
    	Server server = new Server();
    	try {
			server.startServer(null);
		} catch (ServerException e) {
		}
    	//server.registerUser(null);
    	//server.registerUser("");
    	String response = server.registerUser("{\"firstName\":\"asd\"}");
    	
    	response = server.registerUser("{\"firstName\":\"asd\", \"lastName\":\"asdqwe\", \"login\":\"asd123\", \"password\":\"asd\"}");
    	response = server.registerUser("{\"firstNme\":\"asd\", \"lastName\":\"asdqwe\", \"login\":\"asd123\", \"password\":\"asd5678\"}");
    	
    	response = server.loginUser("{\"login\":\"asd13\", \"password\":\"asd5678\"}");
    	response = server.loginUser("{\"login\":\"asd123\", \"password\":\"\"}");
    	response = server.loginUser("{\"login\":\"asd123\", \"password\":\"asd5678\"}");
    	
    	try {
			server.stopServer(null);
		} catch (ServerException e) {
			assertEquals(ServerErrorCode.OTHER_ERROR, e.getServerErrorCode());
		}
    	

    }
    
    /*public void testFileReadWriteByteArray1() throws IOException {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        File file = Files.createFile(tempDir.resolve("test.dat")).toFile();
        FileService.writeByteArrayToBinaryFile(file, arrayToWrite);
        assertTrue(file.exists());
        assertEquals(arrayToWrite.length, file.length());
        byte[] arrayRead = FileService.readByteArrayFromBinaryFile(file);
        assertArrayEquals(arrayToWrite, arrayRead);
    }*/
}
