package com.mythup.myservercommands.mixin;

import com.mythup.myservercommands.MyServerCommandsMod;
import com.mythup.myservercommands.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(CommandSuggestions.class)
public abstract class CommandSuggestionsMixin {
    @Shadow private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow private EditBox input;

    private boolean customOverrodeSuggestions;

    @Inject(method = "updateCommandInfo", at = @At("TAIL"))
    private void onUpdateCommandInfo(CallbackInfo ci) {
        String inputText = input == null ? null : input.getValue();

        if (!MyServerCommandsMod.isTargetServer()) {
            customOverrodeSuggestions = false;
            return;
        }

        if (inputText == null || inputText.isEmpty()) {
            customOverrodeSuggestions = false;
            return;
        }

        if (!inputText.startsWith("/")) {
            inputText = "/" + inputText;
        }

        Suggestions custom = CommandDispatcher.getSuggestions(inputText);
        if (custom == null || custom.isEmpty()) {
            if (customOverrodeSuggestions) {
                this.pendingSuggestions = Suggestions.empty();
                try {
                    CommandSuggestions self = (CommandSuggestions) (Object) this;
                    java.lang.reflect.Field suggestionsField = CommandSuggestions.class.getDeclaredField("suggestions");
                    suggestionsField.setAccessible(true);
                    suggestionsField.set(self, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                customOverrodeSuggestions = false;
            }
            return;
        }

        this.pendingSuggestions = CompletableFuture.completedFuture(custom);
        customOverrodeSuggestions = true;
    }
}