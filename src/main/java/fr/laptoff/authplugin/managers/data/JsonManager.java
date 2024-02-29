package fr.laptoff.authplugin.managers.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.laptoff.authplugin.managers.member.Member;

public class JsonManager {

    private Gson gson;

    public JsonManager(){
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Member.class, new MemberTypeAdapter())
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping().create();


    }

    public String serialize(Member o){
        return this.gson.toJson(o);
    }

    public Member deserialize(String s){
        return this.gson.fromJson(s, Member.class);
    }

}
