/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabAtc;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author pedro
 */
public class ReconheceTag {
    private final Stack<String> nameTags = new Stack();
    ReconheceTag(){
        
    }
    public String divideTag(String expressao,ArrayList<String> tagsPar){
            
            String definicao = "";
            char [] caracterEx = expressao.toCharArray();
            for(int k = 0 ; k < caracterEx.length ; k++){
                int qtdTags = 0;
                    String tagAlerta = "";
                for(int i = 0 ; i < tagsPar.size(); i++){
                    String [] tags = tagsPar.get(i).split(" ");
                    char [] caracterTag = tags[1].toCharArray();
                    for(int j = 0 ; j < caracterTag.length ; j++){
                        if(caracterTag[j] == caracterEx[k] && caracterTag[j] != '+'
                                && caracterTag[j] != '.' && caracterTag[j] != '*' 
                                && caracterTag[j] != '\\'){
                            String [] nomeTag = tags[0].split(":");
                            tagAlerta = tagAlerta+ " "+nomeTag[0];
                            qtdTags++;
                        }
                    }
                }
                if(qtdTags>1){
                    tagAlerta = "| [WARNING] Sobreposicao na definição das TAGS: "+tagAlerta+" |";
                    this.nameTags.push(tagAlerta);
                }else if(qtdTags == 1){
                    this.nameTags.push(tagAlerta);
                }else if(qtdTags == 0){
                    this.nameTags.push("| [ERRO] alfabeto '"+caracterEx[k]+"' nao definido |");
                }
            }
            
            Stack<String> auxNameTags = new Stack();
            
            if(this.nameTags.size()> 2){
                String nomeTags = this.nameTags.pop();
                while(!this.nameTags.isEmpty()){
                    if(this.nameTags.size() == 1){
                        String auxNomeTag = this.nameTags.pop();
                        if(auxNomeTag.equals(nomeTags))
                            auxNameTags.push(nomeTags);
                        else{
                            auxNameTags.push(nomeTags);
                            auxNameTags.push(auxNomeTag);
                        }
                    }else{
                        String auxNomeTag = this.nameTags.pop();
                        if(auxNomeTag.equals(nomeTags)){
                            nomeTags = auxNomeTag;
                        }else{
                            auxNameTags.push(nomeTags);
                            nomeTags = auxNomeTag;
                        }
                    }
                }
                
            }else if(this.nameTags.size() == 2){
                
                String aux1 = this.nameTags.pop();
                String aux2 = this.nameTags.pop();
                if(aux1.equals(aux2)){
                    auxNameTags.push(aux1);
                }else{
                    auxNameTags.push(aux1);
                    auxNameTags.push(aux2);
                }
                
            }else if(this.nameTags.size() == 1){
                auxNameTags.push(this.nameTags.pop());
            }
            
            while(!auxNameTags.isEmpty()){
                definicao = definicao+" "+auxNameTags.pop();
            }
            return definicao;
        }
}
