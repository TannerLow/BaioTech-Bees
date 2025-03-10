package io.github.TannerLow.baiotechbees.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.glasslauncher.mods.alwaysmoreitems.registry.AMIItemRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(AMIItemRegistry.class)
public class AMIItemRegistryMixin {

    @WrapOperation(method = "addItemStack", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    public boolean aaa(Set instance, Object o, Operation<Boolean> original){
        return false;
    }
}