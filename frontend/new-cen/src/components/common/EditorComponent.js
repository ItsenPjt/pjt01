import React, { Component } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

class EditorComponent extends Component {
    
    constructor(props){
        super(props);
        this.state={};
    }
    
    modules = {
        toolbar: [          // 어떤 기능을 사용할지 설정
            //[{ 'font': [] }],     // 웹사이트 기본 폰트가 적용되도록 주석 처리
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline','strike', 'blockquote'],
            [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
            ['link', 'image'],
            [{ 'align': [] }, { 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
            ['clean']
        ],
      }
    
      formats = [
            //'font',
            'header',
            'bold', 'italic', 'underline', 'strike', 'blockquote',
            'list', 'bullet', 'indent',
            'link', 'image',
            'align', 'color', 'background'    
      ]

    render(){
        const { value, onChange } = this.props;

        return (
            <div style={{ height: "650px" }}>
                <ReactQuill 
                    style={{ height: "600px" }} 
                    theme="snow" 
                    modules={this.modules} 
                    formats={this.formats} 
                    value={value || ''} 
                    onChange={
                        (content, delta, source, editor) => onChange(editor.getHTML())
                    }       // onChange(content, delta, source, editor) : 데이터 값 변경에 따른 call back 함수 , editor.getHTML() : editor에 입력된 HTML 태그 가져옴
                />
            </div>
        )
    }
}
export default EditorComponent;