public class Cat{
    /**
     * The name of the cat
     */
    private String name;
    /**
     * Marks if the cat is in a playful state or not
     */
    public boolean playful;
    /**
     * New name counter, will be randomly assigned
     * when the name of the cat is changed
     */
    private int newNameCounter;
    /**
     * Old name of the cat
     */
    private String oldName;
    /**
     * Constructor for a cat, requires a name
     * default state for playful is false
     * @param name of the cat
     */
    public Cat(String name) {
        this.name = name;
        playful = false;
        newNameCounter = (int) (Math.random() * 30);
    }

    /**
     * Given a target, a cat will attempt to pounce
     * and attack it with its claws.
     * If successful, this will return true, otherwise false
     * @param t, A cat's enemy target
     * @return success
     */
//    public boolean attack(Target t){
//        if(target.isRodent()){return true; } else { return false; }
//    }

    /**
     * if the newNameCounter greater than 0, old name is return
     * otherwise newName is returned.
     * @return oldName or name,
     */
    public String getName(){
        if(newNameCounter >0){
            return oldName;
        }else{
            newNameCounter--;
            return name;
        }
    }
}