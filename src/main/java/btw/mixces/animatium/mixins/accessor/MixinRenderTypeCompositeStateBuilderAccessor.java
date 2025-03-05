package btw.mixces.animatium.mixins.accessor;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderType.CompositeState.CompositeStateBuilder.class)
public interface MixinRenderTypeCompositeStateBuilderAccessor {
    @Invoker("setTextureState")
    RenderType.CompositeState.CompositeStateBuilder withTextureState(RenderStateShard.EmptyTextureStateShard emptyTextureStateShard);

    @Invoker("setLayeringState")
    RenderType.CompositeState.CompositeStateBuilder withLayeringState(RenderStateShard.LayeringStateShard layeringStateShard);

    @Invoker("setOutputState")
    RenderType.CompositeState.CompositeStateBuilder withOutputState(RenderStateShard.OutputStateShard outputStateShard);

    @Invoker("setTexturingState")
    RenderType.CompositeState.CompositeStateBuilder withTexturingState(RenderStateShard.TexturingStateShard texturingStateShard);

    @Invoker("createCompositeState")
    RenderType.CompositeState buildCompositeState(boolean affectsOutline);
}
