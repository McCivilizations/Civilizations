package mccivilizations.civilizations;

import mccivilizations.civilizations.common.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Civilizations.MODID, name = Civilizations.NAME, version = Civilizations.VERSION)
public class Civilizations {
    static final String MODID = "civilizations";
    static final String NAME = "Civilizations";
    static final String VERSION = "1.0";

    @Mod.Instance
    public static Civilizations INSTANCE;

    @SidedProxy(clientSide = "mccivilizations.civilizations.client.ClientProxy", serverSide = "mccivilizations.civilizations.server.ServerProxy")
    public static IProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        PROXY.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        PROXY.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        PROXY.postInit();
    }
}