import React, { useState } from "react";
import { Button, Grid, TextField } from "@mui/material";

const AddTodo = (props) => {
    // eslint-disable-next-line
    const [item, setItem] = useState({ title : ""})
    const addItem = props.addItem;

    const onButtonClick = (e) => {
        addItem(item);
        setItem({title: ""});
    }

    const enterKeyEventHandler = (e) => {
        if(e.key === 'Enter'){
            onButtonClick();
        }

    }

    const onInputChange = (e) => {
        setItem({title : e.target.value});
        // console.log(item);
    }

    return (
        <Grid container style={{ marginTop : 20}}>
            <Grid xs={11} md={11} item style={{ paddingRight: 16}}>
                <TextField 
                placeholder="Add todo here" 
                fullWidth 
                onChange={onInputChange} 
                onKeyPress={enterKeyEventHandler}
                value={item.title}/>
            </Grid>
            <Grid xs={1} md={1} item>
                <Button fullWidth style={{ heigh : '100%' }} color="secondary" variant="outlined" onClick={onButtonClick}>
                    +
                </Button>
            </Grid>
        </Grid>
    )
}

export default AddTodo;