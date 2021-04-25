package utils;

public class StringTemplate {

    private String[] parts;
    private int[] templateLengths;

    public StringTemplate(String template) {
        int splits = 1;
        for(int i = 0; i < template.length(); i++) {
            if(template.charAt(i) == '\\') {
                i++;
                continue;
            }

            if(template.charAt(i) == '{') {
                while(++i < template.length() && template.charAt(i) != '}');
                if(i >= template.length()) {
                    break;
                }

                splits++;
            }
        }

        parts = new String[splits];
        templateLengths = new int[splits-1];



    }
}
