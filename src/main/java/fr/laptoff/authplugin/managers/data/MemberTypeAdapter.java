package fr.laptoff.authplugin.managers.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import fr.laptoff.authplugin.managers.member.Member;

import java.io.IOException;
import java.util.UUID;

public class MemberTypeAdapter extends TypeAdapter<Member> {
    @Override
    public void write(JsonWriter writer, Member member) throws IOException {
        writer.beginObject();

        writer.name("pseudo").value(member.getPseudo());
        writer.name("uuid").value(member.getUuid().toString());
        writer.name("identifier").value(member.getIdentifier());
        writer.name("password").value(member.getPassword());
        writer.name("isAuthenticate").value(member.isAuthenticate());

        writer.endObject();
    }

    @Override
    public Member read(JsonReader reader) throws IOException {

        reader.beginObject();

        String pseudo = null;
        UUID uuid = null;
        String identifier = null;
        String password = null;
        boolean isAuthenticate = false;

        while(reader.hasNext()){

            switch(reader.nextName()){
                case "pseudo" :
                    pseudo = reader.nextString();
                    break;

                case "uuid" :
                    uuid = UUID.fromString(reader.nextString());
                    break;

                case "identifier" :
                    identifier = reader.nextString();
                    break;

                case "password" :
                    password = reader.nextString();
                    break;

                case "isAuthenticate" :
                    isAuthenticate = reader.nextBoolean();
            }
        }

        reader.endObject();

        Member mem = new Member(pseudo, uuid, identifier, password);
        mem.setAuthenticate(isAuthenticate);
        return mem;
    }
}
