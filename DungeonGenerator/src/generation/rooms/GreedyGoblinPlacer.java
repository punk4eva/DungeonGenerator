
package generation.rooms;

import components.Area;
import components.rooms.PlainRoom;
import components.rooms.Room;
import components.rooms.Room.DoorStyle;
import components.tiles.PassageTile;
import components.tiles.Tile;
import generation.MultiPlacer;
import graph.Point.Type;
import gui.questions.RoomPlacerSpecifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import utils.PriorityQueue;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.R;

/**
 * Generates a goblin-style "centroidal" dungeon.
 * @author Adam Whittaker
 */
public class GreedyGoblinPlacer extends AbstractRoomPlacer implements MultiPlacer{
    
    
    /**
     * rooms: The list of rooms remaining to add.
     * walls: The list of available walls.
     * maxLength: The maximum length of a corridor.
     */
    private final List<Room> rooms;
    private final PriorityQueue<Wall> walls;
    private final int maxLength;
    
    
    /**
     * Creates a default instance. Uses a random wall ordering and orders the
     * rooms by ascending size.
     * @param a The target area.
     * @param ro The list of rooms.
     * @param maxCorridorLength The maximum corridor length.
     */
    public GreedyGoblinPlacer(Area a, List<Room> ro, int maxCorridorLength){
        this(a, ro, maxCorridorLength, wall -> R.nextDouble(), 
                AbstractRoomPlacer::roomSizeComparator);
    }
    
    /**
     * Creates an instance.
     * @param a The area.
     * @param ro The rooms to place.
     * @param maxCorridorLength The maximum corridor length.
     * @param ordering The function that orders the walls to choose.
     * @param roomComparator The function that sorts the rooms.
     */
    public GreedyGoblinPlacer(Area a, List<Room> ro, int maxCorridorLength, Function<Wall, Double> ordering, Comparator<Room> roomComparator){
        super(a);
        maxLength = maxCorridorLength;
        rooms = ro;
        rooms.sort(roomComparator);
        walls = new PriorityQueue<>(ordering);
    }
    

    @Override
    public void generate(){
        //Places the starting room in the area.
        placeInitialRoom(popStartingRoom());
        
        Room r;
        Wall toRemove = null;
        //Loops through the rooms.
        for(int n=0;n<rooms.size();n++){
            r = rooms.get(n);
            //Rotates the current room
            if(!r.doorStyle.equals(DoorStyle.SOUTH))
                r.randomizeOrientation();
            //A flag to see whether the room could be successfully placed.
            boolean placed = false;
            
            //Loops through the walls to find one to place the room on.
            for(Wall wall : walls){
                //Rotates the room if it needs to face the correct direction.
                if(r.doorStyle.equals(DoorStyle.SOUTH)){
                    if(wall.orientation%2==1) r.orientation = (wall.orientation+2)%4;
                    else r.orientation = wall.orientation;
                }
                int[] coords;
                //Decreases from a random corridor length until the room is 
                //placeable.
                for(int corLength = R.nextInt(maxLength+1);corLength>=0;corLength--){
                    if(corLength==1) continue;
                    coords = wall.getCoordinates(r, corLength);
                    //Places the room if there is space free.
                    if(spaceFree(coords[0], coords[1], r.getWidth(), r.getHeight())){
                        placeRoom(coords[0], coords[1], r, wall, n+1);
                        placed = true;
                        break;
                    }
                }
                if(placed){
                    toRemove = wall;
                    break;
                }
            }
            //Removes the now used up wall if there was a placement.
            if(placed){
                walls.remove(toRemove);
            }else{
                PERFORMANCE_LOG.println(r.name + " is too big to be placed!");
                System.out.println(r.name + " is too big to be placed!");
            }
        }
    }
    
    
    /**
     * Gets the initial room to start the generation with.
     * @return The first room in the list with free door placement range.
     */
    private Room popStartingRoom(){
        Room room;
        Iterator<Room> iter = rooms.iterator();
        //Iterates through the room list and pops the first room with the "ANY"
        //Door placement tag.
        while(iter.hasNext()){
            room = iter.next();
            if(room.doorStyle.equals(DoorStyle.ANY)){
                iter.remove();
                return room;
            }
        }
        //If there is no such room, a new room is generated.
        return new PlainRoom(5 + R.nextInt(5) * 2, 5 + R.nextInt(5) * 2);
    }    
    
    /**
     * Places the starting room.
     * @param room The first room.
     */
    private void placeInitialRoom(Room room){
        //Rotates the room.
        room.randomizeOrientation();
        //Places the room in the center of the Area.
        int x = (area.info.width-room.getWidth())/2;
        int y = (area.info.height-room.getHeight())/2;
        markAndBlit(room, x, y, 0);
        
        //Adds the walls to the list of available walls.
        walls.add(new Wall(x, y, room.getWidth(), 0, room::getEntrance));
        walls.add(new Wall(x+room.getWidth()-1, y, room.getHeight(), 1, room::getEntrance));
        walls.add(new Wall(x, y+room.getHeight()-1, room.getWidth(), 2, room::getEntrance));
        walls.add(new Wall(x, y, room.getHeight(), 3, room::getEntrance));
    }
    
    /**
     * Places a room in the Area.
     * @param x The top-left x.
     * @param y The top-left y.
     * @param room The room to place.
     * @param wall The existing wall that will connect the room.
     * @param roomNumber The room number.
     */
    private void placeRoom(int x, int y, Room room, Wall wall, int roomNumber){
        //Adds the room to the area.
        markAndBlit(room, x, y, roomNumber);
        //Adds the 3 newly exposed walls to the wall list.
        if(room.doorStyle.equals(DoorStyle.ANY)){
            if(wall.orientation!=2) walls.add(new Wall(x, y, room.getWidth(), 0, room::getEntrance));
            if(wall.orientation!=3) walls.add(new Wall(x+room.getWidth()-1, y, room.getHeight(), 1, room::getEntrance));
            if(wall.orientation!=0) walls.add(new Wall(x, y+room.getHeight()-1, room.getWidth(), 2, room::getEntrance));
            if(wall.orientation!=1) walls.add(new Wall(x, y, room.getHeight(), 3, room::getEntrance));
        }
        //Extends a corridor between the new room and the existing complex.
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
    
    /**
     * Adds the Room to the Area.
     * @param r The Room.
     * @param x The top-left x.
     * @param y The top-left y.
     * @param roomNumber The room number.
     */
    private void markAndBlit(Room r, int x, int y, int roomNumber){
        //Marks the surrounding space on the graph to prevent room clipping.
        mark(x+1, y+1, r.getWidth()-2, r.getHeight()-2, roomNumber);
        //Adds the room to the area.
        area.blitRoom(r, x, y);
    }
    
    /**
     * Creates a corridor north from the given (x, y) point.
     * @param x
     * @param y
     * @param wall The wall to extend from.
     * @param room The Room to extend to.
     */
    private void extendNorthCorridor(int x, int y, Wall wall, Room room){
        //Gets the initial point of the corridor randomly.
        x = getCorridorPoint(wall.x, wall.length, room.getWidth());
        //If the room needs no corridor, just creates a door.
        if(y+room.getHeight()-1==wall.y){
            area.map[wall.y][x] = room.getEntrance(area);
        }else{
            //Otherwise creates a corridor.
            area.map[wall.y][x] = wall.doorFunction.apply(area);
            //Adjusts the relevant coordinate until the room is intersected.
            for(y=wall.y-1;true;y--){
                if(area.graph.map[y-1][x].roomNum>-1) break;
                //Generates the floor and wall of the corridor at that point.
                area.map[y][x] = Tile.genFloor(area);
                if(area.map[y][x-1]==null || !area.map[y][x-1].equals(Type.FLOOR)) 
                    area.map[y][x-1] = Tile.genWall(area);
                if(area.map[y][x+1]==null || !area.map[y][x+1].equals(Type.FLOOR)) 
                    area.map[y][x+1] = Tile.genWall(area);
                //Marks the space in the Area as corridor.
                area.graph.map[y][x].roomNum = -2;
                area.graph.map[y][x-1].roomNum = -2;
                area.graph.map[y][x+1].roomNum = -2;
            }
            //Creates an entrance for the room.
            area.map[y][x] = room.getEntrance(area);
        }
    }
    
    /**
     * Creates a corridor east from the given (x, y) point.
     * See extendNorthCorridor() for fuller method Javadocs.
     * @param x
     * @param y
     * @param wall The wall to extend from.
     * @param room The Room to extend to.
     */
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
    
    /**
     * Creates a corridor south from the given (x, y) point.
     * See extendNorthCorridor() for fuller method Javadocs.
     * @param x
     * @param y
     * @param wall The wall to extend from.
     * @param room The Room to extend to.
     */
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
    
    /**
     * Creates a corridor west from the given (x, y) point.
     * See extendNorthCorridor() for fuller method Javadocs.
     * @param x
     * @param y
     * @param wall The wall to extend from.
     * @param room The Room to extend to.
     */
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
    
    /**
     * Gets a random point on the existing wall that will result in a corridor
     * intersecting the new wall.
     * @param wallX The start coordinate of the wall.
     * @param wallLength The length of the existing wall.
     * @param roomLength The length of the new wall.
     * @return A coordinate to start the corridor at.
     */
    private int getCorridorPoint(int wallX, int wallLength, int roomLength){
        if(wallLength<roomLength){
            return wallX + R.nextInt(wallLength-2) + 1;
        }else return wallX + (wallLength-roomLength)/2 + R.nextInt(roomLength-2) + 1;
    }
    
    
    /**
     * Represents a wall of a room.
     */
    public static class Wall{
    
        
        /**
         * x, y: The position of the top-left of the wall.
         * length: The length of the wall.
         * orientation: The rotation code of the wall, following the convention
         * given in the Area class.
         * doorFunction: Creates a door for this wall.
         */
        private final int x, y, length, orientation;
        private final Function<Area, PassageTile> doorFunction;
        
        /**
         * Creates a new instance by initializing the fields in order of 
         * declaration.
         * @param _x
         * @param _y
         * @param len
         * @param orient
         * @param func
         */
        private Wall(int _x, int _y, int len, int orient, Function<Area, PassageTile> func){
            x = _x;
            y = _y;
            length = len;
            orientation = orient;
            doorFunction = func;
        }
        
        /**
         * Gets the coordinates of centre of the wall relative to the given 
         * room.
         * @param r The room.
         * @param corLength The length of the corridor.
         * @return
         */
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
    
    
    public static final RoomPlacerSpecifier<GreedyGoblinPlacer> 
            GREEDY_GOBLIN_SPECIFIER;
    static{
        try{
            GREEDY_GOBLIN_SPECIFIER = new RoomPlacerSpecifier<>(
                    GreedyGoblinPlacer.class.getConstructor(Area.class, 
                            List.class, int.class),
                    GreedyGoblinPlacer.class, "Greedy Goblin Placer", 
                    "Design the greedy goblin algorithm");
        }catch(NoSuchMethodException e){
            throw new IllegalStateException(e);
        }
    }

}
