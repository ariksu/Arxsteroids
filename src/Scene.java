import java.awt.*;

/**
 * Created by Ariksu on 8/10/2015.
 */
public class Scene implements Runnable {

    private final int INIT_SIZE=10;
    private static java.util.List<Actor> sceneStorage;
    static Actor[] sortedStorage;
    static int storageSize, storageFill, typenumber;
    private static long camX, camY, camZoom;
    private static long maxuid;

    public void BasicModel (){
        this.storageSize=INIT_SIZE;
        this.sortedStorage = new Actor[storageSize];
        this.maxuid=0;
        this.storageFill=0;
        typestorage= new ActorType[10];
        typenumber=0;

    }
    public class Actor {
        private long X,Y;
        private String name;
        private int id;
        private java.util.List<Actor> storageLink;
        private Actor[] idStorageLink;

        public Actor(int x, int y, String name){
            this.X=x;
            this.Y=y;
            this.name=name;
            for (ActorType i : typestorage){
                //if (i.name==this.name) this.
            }
        }


    }

    public void addNewType(String newname, Polygon newshape, int maxlife, int minattack){
        typestorage[typenumber]= new ActorType(newname, newshape, maxlife, minattack);
    }

    static ActorType[] typestorage;

    class ActorType {
        private String name;
        private Polygon shape;
        private int life;
        private int attack;

        public ActorType(String name, Polygon shape, int life, int attack) {
            this.name = name;
            this.shape = shape;
            this.life = life;
            this.attack = attack;
        }
    }



    private class Vector {
        private int x,y;
    }

    public void run (){

    }
}
