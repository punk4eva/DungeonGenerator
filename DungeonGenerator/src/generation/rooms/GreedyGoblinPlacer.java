
package generation.rooms;

import components.Area;
import components.rooms.PlainRoom;
import components.rooms.Room;
import components.rooms.Room.DoorStyle;
import components.tiles.PassageTile;
import components.tiles.Tile;
import generation.MultiPlacer;
import graph.Point.Type;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;
import utils.PriorityQueue;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.R;
import static utils.Utils.SPEED_TESTER;

/**
 * Generates a goblin-style "centroidal" dungeon.
 * @author Adam Whittaker
 */
public class GreedyGoblinPlacer extends AbstractRoomPlacer implements MultiPlacer{
    
    
    private final LinkedList<Room> rooms;
    private final PriorityQueue<Wall> walls;
    private final int maxLength;
    
    
    public GreedyGoblinPlacer(Area a, int maxL, LinkedList<Room> ro, Function<Wall, Double> ordering, Comparator<Room> roomComparator){
        super(a);
        maxLength = maxL;
        rooms = ro;
        rooms.sort(roomComparator);
        walls = new PriorityQueue<>(ordering);
    }
    

    @Override
    public void generate(){
        placeInitialRoom(popStartingRoom());
        
        Room r;
        Wall toRemove = null;
        for(int n=0;n<rooms.size();n++){
            r = rooms.get(n);
            if(!r.doorStyle.equals(DoorStyle.SOUTH))
                r.randomizeOrientation();
            
            boolean placed = false;
            
            for(Wall wall : walls){
                if(r.doorStyle.equals(DoorStyle.SOUTH)){
                    if(wall.orientation%2==1) r.orientation = (wall.orientation+2)%4;
                    else r.orientation = wall.orientation;
                }
                int[] c;
                for(int corLength = R.nextInt(maxLength+1);corLength>=0;corLength--){
                    if(corLength==1) continue;
                    c = wall.getCoordinates(r, corLength);
                    if(spaceFree(c[0], c[1], r.getWidth(), r.getHeight())){
                        placeRoom(c[0], c[1], r, wall, n+1);
                        placed = true;
                        break;
                    }
                }
                if(placed){
                    toRemove = wall;
                    break;
                }
            }
            if(placed){
                walls.remove(toRemove);
            }else{
                PERFORMANCE_LOG.println(r.name + " is too big to be placed!");
                System.out.println(r.name + " is too big to be placed!");
            }
        }
        SPEED_TESTER.test("Goblin rooms placed");
    }
    
    private Room popStartingRoom(){
        Room room;
        Iterator<Room> iter = rooms.iterator();
        
        while(iter.hasNext()){
            room = iter.next();
            if(room.doorStyle.equals(DoorStyle.ANY)){
                iter.remove();
                return room;
            }
        }
        return new PlainRoom(5 + R.nextInt(5) * 2, 5 + R.nextInt(5) * 2);
    }
    
    private void placeInitialRoom(Room room){
        room.randomizeOrientation();
        int x = (area.info.width-room.getWidth())/2;
        int y = (area.info.height-room.getHeight())/2;
        markAndBlit(room, x, y, 0);
        
        walls.add(new Wall(x, y, room.getWidth(), 0, room::getEntrance));
        walls.add(new Wall(x+room.getWidth()-1, y, room.getHeight(), 1, room::getEntrance));
        walls.add(new Wall(x, y+room.getHeight()-1, room.getWidth(), 2, room::getEntrance));
        walls.add(new Wall(x, y, room.getHeight(), 3, room::getEntrance));
    }
    
    private void placeRoom(int x, int y, Room room, Wall wall, int n){
        markAndBlit(room, x, y, n);
        
        if(room.doorStyle.equals(DoorStyle.ANY)){
            if(wall.orientation!=2) walls.add(new Wall(x, y, room.getWidth(), 0, room::getEntrance));
            if(wall.orientation!=3) walls.add(new Wall(x+room.getWidth()-1, y, room.getHeight(), 1, room::getEntrance));
            if(wall.orientation!=0) walls.add(new Wall(x, y+room.getHeight()-1, room.getWidth(), 2, room::getEntrance));
            if(wall.orientation!=1) walls.add(new Wall(x, y, room.getHeight(), 3, room::getEntrance));
        }
        
        switch(wall.orientation){
            case 0: extendNorthCorridor(x, y, wall, room);
                break;
            case 1: extendEastCorridor(x, y, wall, room);
                break;
            case 2: extendSouthCorridor(x, y, wall, room);
                break;
            case 3: extendWestCorridor(x, y, wall, room);
                break;
        }
    }
    
    private void markAndBlit(Room r, int x, int y, int n){
        mark(x+1, y+1, r.getWidth()-2, r.getHeight()-2, n);
        area.blitRoom(r, x, y);
    }
    
    private void extendNorthCorridor(int x, int y, Wall wall, Room room){
        x = getCorridorPoint(wall.x, wall.length, room.getWidth());
        if(y+room.getHeight()-1==wall.y){
            area.map[wall.y][x] = room.getEntrance(area);
        }else{
            area.map[wall.y][x] = wall.doorFunction.apply(area);
            for(y=wall.y-1;true;y--){
                if(area.graph.map[y-1][x].roomNum>-1) break;
                area.map[y][x] = Tile.genFloor(area);
                if(area.map[y][x-1]==null || !area.map[y][x-1].equals(Type.FLOOR)) 
                    area.map[y][x-1] = Tile.genWall(area);
                if(area.map[y][x+1]==null || !area.map[y][x+1].equals(Type.FLOOR)) 
                    area.map[y][x+1] = Tile.genWall(area);
                area.graph.map[y][x].roomNum = -2;
                area.graph.map[y][x-1].roomNum = -2;
                area.graph.map[y][x+1].roomNum = -2;
            }
            area.map[y][x] = room.getEntrance(area);
        }
    }
    
    private void extendEastCorridor(int x, int y, Wall wall, Room room){
        y = getCorridorPoint(wall.y, wall.length, room.getHeight());
        if(x==wall.x){
            area.map[y][x] = room.getEntrance(area);
        }else{
            area.map[y][wall.x] = wall.doorFunction.apply(area);
            for(x=wall.x+1;true;x++){
                if(area.graph.map[y][x+1].roomNum>-1) break;
                area.map[y][x] = Tile.genFloor(area);
                if(area.map[y+1][x]==null || !area.map[y+1][x].equals(Type.FLOOR)) 
                    area.map[y+1][x] = Tile.genWall(area);
                if(area.map[y-1][x]==null || !area.map[y-1][x].equals(Type.FLOOR)) 
                    area.map[y-1][x] = Tile.genWall(area);
                area.graph.map[y][x].roomNum = -2;
                area.graph.map[y+1][x].roomNum = -2;
                area.graph.map[y-1][x].roomNum = -2;
            }
            area.map[y][x] = room.getEntrance(area);
        }
    }
    
    private void extendSouthCorridor(int x, int y, Wall wall, Room room){
        x = getCorridorPoint(wall.x, wall.length, room.getWidth());
        if(y==wall.y){
            area.map[wall.y][x] = room.getEntrance(area);
        }else{
            area.map[wall.y][x] = wall.doorFunction.apply(area);
            for(y=wall.y+1;true;y++){
                if(area.graph.map[y+1][x].roomNum>-1) break;
                area.map[y][x] = Tile.genFloor(area);
                if(area.map[y][x-1]==null || !area.map[y][x-1].equals(Type.FLOOR)) 
                    area.map[y][x-1] = Tile.genWall(area);
                if(area.map[y][x+1]==null || !area.map[y][x+1].equals(Type.FLOOR)) 
                    area.map[y][x+1] = Tile.genWall(area);
                area.graph.map[y][x].roomNum = -2;
                area.graph.map[y][x-1].roomNum = -2;
                area.graph.map[y][x+1].roomNum = -2;
            }
            area.map[y][x] = room.getEntrance(area);
        }
    }
    
    private void extendWestCorridor(int x, int y, Wall wall, Room room){
        y = getCorridorPoint(wall.y, wall.length, room.getHeight());
        if(x+room.getWidth()-1==wall.x){
            area.map[y][wall.x] = room.getEntrance(area);
        }else{
            area.map[y][wall.x] = wall.doorFunction.apply(area);
            for(x=wall.x-1;true;x--){
                if(area.graph.map[y][x-1].roomNum>-1) break;
                area.map[y][x] = Tile.genFloor(area);
                if(area.map[y+1][x]==null || !area.map[y+1][x].equals(Type.FLOOR)) 
                    area.map[y+1][x] = Tile.genWall(area);
                if(area.map[y-1][x]==null || !area.map[y-1][x].equals(Type.FLOOR)) 
                    area.map[y-1][x] = Tile.genWall(area);
                area.graph.map[y][x].roomNum = -2;
                area.graph.map[y+1][x].roomNum = -2;
                area.graph.map[y-1][x].roomNum = -2;
            }
            area.map[y][x] = room.getEntrance(area);
        }
    }
    
    private int getCorridorPoint(int wallX, int wallLength, int roomLength){
        if(wallLength<roomLength){
            return wallX + R.nextInt(wallLength-2) + 1;
        }else return wallX + (wallLength-roomLength)/2 + R.nextInt(roomLength-2) + 1;
    }
    
    
    public static class Wall{
    
        private final int x, y, length, orientation;
        private final Function<Area, PassageTile> doorFunction;
        
        private Wall(int _x, int _y, int len, int orient, Function<Area, PassageTile> func){
            x = _x;
            y = _y;
            length = len;
            orientation = orient;
            doorFunction = func;
        }
        
        private int[] getCoordinates(Room r, int corLength){
            switch(orientation){
                case 0: return new int[]{x + (length-r.getWidth())/2,
                        y - r.getHeight() - corLength + 1};
                case 1: return new int[]{x  + corLength, 
                        y + (length-r.getHeight())/2};
                case 2: return new int[]{x + (length-r.getWidth())/2,
                        y + corLength};
                default: return new int[]{x - r.getWidth() - corLength + 1, 
                        y + (length-r.getHeight())/2};
            }
        }
        
    }

}
