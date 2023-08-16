package gourp77.board;

import gourp77.workspace.Workspace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getIdTest() {
        Board b = new Board("a");
        b.setId(0);
        assertEquals(0, b.getId(), "id should be set to 0");
    }

    @Test
    void getIdTest_NoInitialization() {
        Board b = new Board("a");
        assertEquals(b.getId(), 0);
    }
    @Test
    void setIdTest() {
        Board b = new Board("a");
        b.setId(1);
        assertEquals(1, b.getId(), "id should be set to 1");
    }

    @Test
    void getNameTest() {
        Board b = new Board("a");
        assertEquals("a", b.getName(), "name should be \"a\"");
    }

    @Test
    void getNameTest_InvalidParameter() {
        Board b = new Board(null);
        assertNull(b.getName(), "name should be null");
    }

    @Test
    void setNameTest() {
        Board b = new Board("a");
        b.setName("b");
        assertEquals("b", b.getName(), "name should be set to \"b\"");
    }

    @Test
    void setNameTest_InvalidParameter() {
        Board b = new Board("a");
        b.setName(null);
        assertNull(b.getName(), "name should be set to null");
    }

    @Test
    void getWorkspaceTest() {
        Board b = new Board("a");
        Workspace ws = new Workspace("a", "a");
        b.setWorkspace(ws);
        assertEquals(ws, b.getWorkspace(), "workspace should be set to ws");
    }

    @Test
    void getWorkspaceTest_BeforeSetWorkspace() {
        Board b = new Board("a");
        assertNull(b.getWorkspace(), "there should be no workspace");
    }

    @Test
    void setWorkspaceTest() {
        Board b = new Board("a");
        Workspace ws = new Workspace("b", "b");
        b.setWorkspace(ws);
        assertEquals(ws, b.getWorkspace(), "workspace should be set to ws");
    }

    @Test
    void setWorkspaceTest_InvalidParameter() {
        Board b = new Board("a");
        b.setWorkspace(null);
        assertNull(b.getWorkspace(), "workspace should be null");
    }
}